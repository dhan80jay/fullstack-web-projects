import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { AppointmentEntity } from '../../model/appointment/appointment';
import { DoctorEntity } from '../../model/doctor/doctor';

@Injectable({
  providedIn: 'root',
})
export class DoctorService {
  url = 'http://localhost:8080/api/v1/doctors';

  constructor(private httpClient: HttpClient) {}

  getAllDoctors() {
    return this.httpClient.get<DoctorEntity[]>(`${this.url}`);
  }

  getDoctorById(id: any) {
    return this.httpClient.get<DoctorEntity>(`${this.url}/${id}`);
  }

  getAppointmentByDoctorId(id: any) {
    return this.httpClient.get<AppointmentEntity[]>(`${this.url}/appointments/${id}`);
  }

  addDoctor(doctor: DoctorEntity) {
    return this.httpClient.post<DoctorEntity>(`${this.url}/register`, doctor);
  }

  updateDoctor(doctor: DoctorEntity, id: any) {
    return this.httpClient.put<DoctorEntity>(`${this.url}/${id}`, doctor);
  }
  getLoggedInDoctor() {
    return this.httpClient.get<DoctorEntity>(`${this.url}/me`);
  }
  deleteDoctor(id: any) {
    return this.httpClient.delete<string>(`${this.url}/${id}`);
  }
  updateAppointmentStatus(doctorId: number, appointmentId: number, status: string) {
    return this.httpClient.put<AppointmentEntity>(
      `${this.url}/${doctorId}/appointments/${appointmentId}/${status}`,
      {},
    );
  }
  
}
