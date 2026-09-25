import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { MedicineEntity } from '../../model/medicine/medicine';

@Injectable({
  providedIn: 'root',
})
export class MedicineService {

  url = 'https://hospital-backend-production-a45c.up.railway.app/api/v1/medicines';

  constructor(private httpClient: HttpClient) {}

  addMedicine(medicine: MedicineEntity) {
    return this.httpClient.post<MedicineEntity>(
      this.url,
      medicine
    );
  }

  getAllMedicines() {
    return this.httpClient.get<MedicineEntity[]>(
      this.url
    );
  }

  getMedicineById(id: number) {
    return this.httpClient.get<MedicineEntity>(
      `${this.url}/${id}`
    );
  }

  updateMedicine(id: number, medicine: MedicineEntity) {
    return this.httpClient.put<MedicineEntity>(
      `${this.url}/${id}`,
      medicine
    );
  }

  deleteMedicine(id: number) {
    return this.httpClient.delete<void>(
      `${this.url}/${id}`
    );
  }
}