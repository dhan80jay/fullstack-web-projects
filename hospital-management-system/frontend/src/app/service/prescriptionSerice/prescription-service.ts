import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { PrescriptionEntity } from '../../model/prescription/prescription';
import { MedicineEntity } from '../../model/medicine/medicine';

@Injectable({
  providedIn: 'root',
})
export class PrescriptionService {
  url = 'https://hospital-backend-production-a45c.up.railway.app/api/v1/prescriptions';

  constructor(private httpClient: HttpClient) {}

  createPrescription(prescription: PrescriptionEntity) {
    return this.httpClient.post<PrescriptionEntity>(`${this.url}`, prescription);
  }

  getPrescriptionById(id: number) {
    return this.httpClient.get<PrescriptionEntity>(`${this.url}/${id}`);
  }

  getAllPrescriptions() {
    return this.httpClient.get<PrescriptionEntity[]>(`${this.url}`);
  }

  getPrescriptionsByDoctorId(id: any) {
    return this.httpClient.get<PrescriptionEntity[]>(
      `https://hospital-backend-production-a45c.up.railway.app/api/v1/prescriptions/doctor/${id}`,
    );
  }

  updatePrescription(id: number, prescription: PrescriptionEntity) {
    return this.httpClient.put<PrescriptionEntity>(`${this.url}/${id}`, prescription);
  }

  deletePrescription(id: any) {
    return this.httpClient.delete(`${this.url}/${id}`, { responseType: 'text' });
  }

  getPrescriptionForUpdate(id: number) {
    return this.httpClient.get<any>(`${this.url}/update/${id}`);
  }

  getMedicinesByPrescriptionId(prescriptionId: number) {
    return this.httpClient.get<MedicineEntity[]>(`${this.url}/${prescriptionId}/medicines`);
  }

  getPrescriptionByAppointmentId(appointmentId: number) {
    return this.httpClient.get<PrescriptionEntity>(`${this.url}/appointment/${appointmentId}`);
  }
}
