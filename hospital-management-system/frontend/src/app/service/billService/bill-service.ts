import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { BillEntity } from '../../model/bill/bill';
import { AppointmentEntity } from '../../model/appointment/appointment';

@Injectable({
  providedIn: 'root',
})
export class BillService {
  url = 'https://hospital-backend-production-a45c.up.railway.app/api/v1/bills';

  constructor(private httpClient: HttpClient) {}

  createBill(bill: BillEntity) {
    return this.httpClient.post<BillEntity>(`${this.url}`, bill);
  }

  getAllBills() {
    return this.httpClient.get<BillEntity[]>(this.url);
  }

  getAppointmentsForBills() {
    return this.httpClient.get<Record<number, AppointmentEntity>>(`${this.url}/appointments`);
  }

  getAppointmentByBillId(id: number) {
    return this.httpClient.get<AppointmentEntity>(`${this.url}/${id}/appointment`);
  }

  getBillById(id: number) {
    return this.httpClient.get<BillEntity>(`${this.url}/${id}`);
  }

  updateBill(id: number, bill: BillEntity) {
    return this.httpClient.put<BillEntity>(`${this.url}/${id}`, bill);
  }

  deleteBill(id: number) {
    return this.httpClient.delete(`${this.url}/${id}`, { responseType: 'text' });
  }

  getBillsByPatient() {
  return this.httpClient.get<BillEntity[]>(`${this.url}/my`);
}
}
