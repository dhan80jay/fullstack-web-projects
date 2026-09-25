import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { PatientEntity } from '../../model/patient/patient-entity';
import { HealthInfo } from '../../model/patient/health-info';

@Injectable({
  providedIn: 'root',
})
export class PatientService {
  url = 'https://hospital-backend-production-a45c.up.railway.app/api/v1/patients';

  constructor(private httpClient: HttpClient) {}

  getAllPatients() {
    return this.httpClient.get<PatientEntity[]>(`${this.url}`);
  }

  getPatientById(id: any) {
    return this.httpClient.get<PatientEntity>(`${this.url}/${id}`);
  }

  updateHealthInformation(id: number, patient: HealthInfo) {
    return this.httpClient.put<HealthInfo>(`${this.url}/health-information/${id}`, patient);
  }

  getPatientByUsername() {
    return this.httpClient.get<PatientEntity>(`${this.url}/me`);
  }

  addPatient(patient: PatientEntity) {
    return this.httpClient.post<PatientEntity>(`${this.url}/register`, patient);
  }

  updatePatient(patient: PatientEntity, id: any) {
    return this.httpClient.put(`${this.url}/${id}`, patient);
  }

  deletePatient(id: any) {
    return this.httpClient.delete(`${this.url}/${id}`);
  }
}
