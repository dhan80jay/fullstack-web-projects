import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { PatientEntity } from '../../model/patient/patient-entity';

@Injectable({
  providedIn: 'root',
})
export class AdminService {
    
  url = 'http://localhost:8080/api/v1/admin/patients';
 
  constructor(private httpClient:HttpClient) {
    
  }

  adminAddPatient(patient:PatientEntity){
    return this.httpClient.post(`${this.url}`,patient);
  }
  
}

