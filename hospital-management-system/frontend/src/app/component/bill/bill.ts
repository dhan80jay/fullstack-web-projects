import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { RouterLink } from '@angular/router';
import { AppointmentEntity } from '../../model/appointment/appointment';
import { BillEntity } from '../../model/bill/bill';
import { AppointmentService } from '../../service/appointment/appointment-service';
import { BillService } from '../../service/billService/bill-service';
import { NgxPaginationModule } from 'ngx-pagination';
import { PaymentStatus } from '../../enum/payment-status';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-bill',
  imports: [RouterLink, NgxPaginationModule, FormsModule, CommonModule],
  templateUrl: './bill.html',
  styleUrl: './bill.css',
})
export class Bill implements OnInit {
  p: number = 1;

  bills: BillEntity[] = [];

  sortedBill: BillEntity[] = [];

  appointments: AppointmentEntity[] = [];

  today = new Date();

  searchText: string = '';

  ascending = true;

  totalBills = 0;
  pendingBills = 0;
  paidBills = 0;

  PaymentStatus = PaymentStatus;

  constructor(
    private cdr: ChangeDetectorRef,
    private appointmentService: AppointmentService,
    private billService: BillService,
  ) {}

  ngOnInit(): void {
    this.loadBills();
  }

  // =====================================
  // LOAD BILLS + APPOINTMENTS
  // =====================================

  loadBills(): void {
    this.billService.getAllBills().subscribe({
      next: (bills) => {
        this.appointmentService.getAllAppointments().subscribe({
          next: (appointments) => {
            this.bills = bills.map((bill) => {
              const appointment = appointments.find(
                (appointment) => appointment.bill?.id === bill.id,
              );

              return {
                ...bill,
                appointment: appointment!,
              };
            });

            this.sortedBill = [...this.bills];

            this.countTotal();

            this.cdr.detectChanges();
          },

          error: (err) => {
            console.error('Error loading appointments:', err);
          },
        });
      },

      error: (err) => {
        console.error('Error loading bills:', err);
      },
    });
  }

  // =====================================
  // SEARCH BY PATIENT NAME
  // =====================================

  searchBillByPatientName(): void {
    if (this.searchText.trim() === '') {
      this.sortedBill = this.bills;
      this.p = 1;
    } else {
      this.sortedBill = this.bills.filter(
        (bill) =>
          bill.appointment.patient.firstName
            .toLowerCase()
            .includes(this.searchText.toLowerCase()) ||
          bill.appointment.patient.lastName.toLowerCase().includes(this.searchText.toLowerCase()),
      );

      this.p = 1;
      this.cdr.detectChanges();
    }
  }

  // =====================================
  // A → Z
  // =====================================

filterPatientsByNames(): void {

  const sorted = [...this.sortedBill].sort((a, b) => {

    const result =
      a.appointment.patient.firstName.localeCompare(
        b.appointment.patient.firstName
      );

    return this.ascending ? result : -result;

  });

  this.sortedBill = sorted;

  this.ascending = !this.ascending;

  this.p = 1;

  this.cdr.detectChanges();
}  // =====================================
  // DELETE BILL
  // =====================================

  deleteBill(id: any): void {
    if (!id) {
      return;
    }

    const confirmDelete = confirm('Are you sure you want to delete this bill?');

    if (!confirmDelete) {
      return;
    }

    this.billService.deleteBill(id).subscribe({
      next: () => {
        alert('Deleted Successfully!');

        this.loadBills();
      },

      error: (error) => {
        console.error('Error deleting bill:', error);

        alert('Unable to delete bill.');
      },
    });
  }

  // =====================================
  // COUNT BILLS
  // =====================================

  countTotal(): void {
    this.totalBills = this.bills.length;

    this.pendingBills = this.bills.filter(
      (bill) => bill.paymentStatus === PaymentStatus.PENDING,
    ).length;

    this.paidBills = this.bills.filter((bill) => bill.paymentStatus === PaymentStatus.PAID).length;
  }

  // =====================================
  // PRINT
  // =====================================

  printBill(): void {
    window.print();
  }
}
