import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { AppointmentEntity } from '../../model/appointment/appointment';
import { UpdateAppointmentDto } from '../../model/appointment/update-appointment.dto';
import { BookAppointmentDto } from '../../model/appointment/book-appointment-dto';

@Injectable({
  providedIn: 'root',
})
export class AppointmentService {
  url = 'https://hospital-backend-production-a45c.up.railway.app/api/v1/appointments';

  constructor(private httClient: HttpClient) {}

  getAppointmentsByPatientId(id: any) {
    return this.httClient.get<AppointmentEntity[]>(`${this.url}/patient/${id}`);
  }

  cancelAppointment(appointmentId: number) {
    return this.httClient.put<AppointmentEntity>(`${this.url}/patient/${appointmentId}`,{});
  }

  getAppointmentById(id: any) {
    return this.httClient.get<AppointmentEntity>(`${this.url}/${id}`);
  }

  getAppointmentByPrescriptionId(id: any) {
    return this.httClient.get<AppointmentEntity>(`${this.url}/prescription/${id}`);
  }

  getAllAppointments() {
    return this.httClient.get<AppointmentEntity[]>(`${this.url}`);
  }

  updateAppointment(appointment: UpdateAppointmentDto, id: any) {
    return this.httClient.put<UpdateAppointmentDto>(`${this.url}/${id}`, appointment);
  }

  createAppointment(appointment: AppointmentEntity) {
    return this.httClient.post<AppointmentEntity>(`${this.url}`, appointment);
  }

  bookAppointmentByPatient(appointment: BookAppointmentDto, id: any) {
    return this.httClient.post<BookAppointmentDto>(`${this.url}/patient/${id}`, appointment);
  }

  deleteAppointment(id: any) {
    return this.httClient.delete(`${this.url}/${id}`, { responseType: 'text' });
  }
}
