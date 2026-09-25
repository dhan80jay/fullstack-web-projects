import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { PatientEntity } from '../../model/patient/patient-entity';

@Injectable({
  providedIn: 'root',
})
export class AdminService {
    
  url = 'https://hospital-backend-production-a45c.up.railway.app/api/v1/admin/patients';
 
  constructor(private httpClient:HttpClient) {
    
  }

  adminAddPatient(patient:PatientEntity){
    return this.httpClient.post(`${this.url}`,patient);
  }
  
}

