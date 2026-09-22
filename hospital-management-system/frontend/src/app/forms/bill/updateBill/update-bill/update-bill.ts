import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { DatePipe, DecimalPipe } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { AppointmentEntity } from '../../../../model/appointment/appointment';
import { BillEntity } from '../../../../model/bill/bill';
import { AppointmentService } from '../../../../service/appointment/appointment-service';
import { BillService } from '../../../../service/billService/bill-service';

@Component({
  selector: 'app-update-bill',
  imports: [FormsModule, RouterLink, DatePipe, DecimalPipe],
  templateUrl: './update-bill.html',
  styleUrl: './update-bill.css',
})
export class UpdateBill implements OnInit {
  billId!: number;

  selectedAppointmentId!: number;

  appointments: AppointmentEntity[] = [];

  bill: BillEntity = {
    consultationFee: 0,
    medicineCharges: 0,
    otherCharges: 0,
    totalAmount: 0,
    paymentStatus: null as any,
    appointment: null as any,
    billingDate: '',
  };

  constructor(
    private billService: BillService,
    private appointmentService: AppointmentService,
    private route: ActivatedRoute,
    private router: Router,
    private cdr: ChangeDetectorRef,
  ) {}

  ngOnInit(): void {
    this.billId = Number(this.route.snapshot.paramMap.get('id'));

    this.loadBill();
  }

  loadAppointments(): void {
    this.appointmentService.getAllAppointments().subscribe({
      next: (response) => {
        this.appointments = response;

        /*
         * If selectedAppointmentId was not
         * obtained from the bill response,
         * find the appointment using bill.id.
         */
        if (!this.selectedAppointmentId) {
          const appointment = this.appointments.find(
            (appointment) => appointment.bill?.id === this.billId,
          );

          if (appointment) {
            this.selectedAppointmentId = Number(appointment.id);

            this.bill.appointment = appointment;
          }
        }

        this.cdr.detectChanges();
      },

      error: (error) => {
        console.error('Error loading appointments:', error);
      },
    });
  }
  loadBill(): void {
    this.billService.getBillById(this.billId).subscribe({
      next: (response) => {
        this.bill = {
          id: response.id,
          billingDate: response.billingDate,
          consultationFee: response.consultationFee,
          medicineCharges: response.medicineCharges,
          otherCharges: response.otherCharges,
          totalAmount: response.totalAmount,
          paymentStatus: response.paymentStatus,
          appointment: response.appointment,
        };

        /*
         * If appointment is already present
         * in bill response, use it.
         */
        if (response.appointment?.id) {
          this.selectedAppointmentId = Number(response.appointment.id);
        }

        /*
         * Load appointments after bill is loaded.
         * This allows us to find the appointment
         * using bill.id if appointment is not
         * returned inside the bill.
         */
        this.loadAppointments();

        this.cdr.detectChanges();
      },

      error: (error) => {
        alert('Unable to load bill.');
      },
    });
  }
  totalAmount(): number {
    const consultationFee = Number(this.bill.consultationFee) || 0;

    const medicineCharges = Number(this.bill.medicineCharges) || 0;

    const otherCharges = Number(this.bill.otherCharges) || 0;

    return consultationFee + medicineCharges + otherCharges;
  }

  updateBill(): void {
    const selectedAppointment = this.appointments.find(
      (appointment) => appointment.id === this.selectedAppointmentId,
    );

    if (!selectedAppointment) {
      alert('Please select an appointment.');

      return;
    }

    this.bill.appointment = selectedAppointment;

    this.bill.totalAmount = this.totalAmount();

    console.log('Bill before update:', this.bill);

    this.billService.updateBill(this.billId, this.bill).subscribe({
      next: (response) => {
        alert('Bill updated successfully!');

        this.router.navigate(['/dashboard/admin/billing']);
      },
      error: (error) => {
        alert(error.error?.message || 'Failed to update bill.');
      },
    });
  }

  printPrescription(): void {
    window.print();
  }
}
