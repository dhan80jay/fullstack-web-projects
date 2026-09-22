import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { DatePipe, DecimalPipe } from '@angular/common';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { AppointmentEntity } from '../../../model/appointment/appointment';
import { BillEntity } from '../../../model/bill/bill';
import { AppointmentService } from '../../../service/appointment/appointment-service';
import { BillService } from '../../../service/billService/bill-service';


@Component({
  selector: 'app-view-bill',
  imports: [RouterLink, DatePipe, DecimalPipe],
  templateUrl: './view-bill.html',
  styleUrl: './view-bill.css',
})
export class ViewBill implements OnInit {
  billId!: number;

  bill: BillEntity = {
    consultationFee: 0,
    medicineCharges: 0,
    otherCharges: 0,
    totalAmount: 0,
    paymentStatus: null as any,
    appointment: null as any,
    billingDate: '',
  };

  appointment: AppointmentEntity = null as any;

  appointments: AppointmentEntity[] = [];

  constructor(
    private billService: BillService,
    private appointmentService: AppointmentService,
    private route: ActivatedRoute,
    private router: Router,
    private cdr:ChangeDetectorRef
  ) {}

  // --------------------------------
  // Component initialization
  // --------------------------------

  ngOnInit(): void {
    this.billId = Number(this.route.snapshot.paramMap.get('id'));

    this.loadBill();
  }

  // --------------------------------
  // Load Bill
  // --------------------------------

  loadBill(): void {
    this.billService.getBillById(this.billId).subscribe({
      next: (response) => {
        this.bill = response;

        console.log('Bill:', this.bill);

        this.loadAppointments();
        this.cdr.detectChanges();
      },

      error: (error) => {
        console.error('Error loading bill:', error);

        alert('Unable to load bill.');

        this.router.navigate(['/dashboard/admin/billing']);
      },
    });
  }

  // --------------------------------
  // Load Appointments
  // --------------------------------

  loadAppointments(): void {
    this.appointmentService.getAllAppointments().subscribe({
      next: (response) => {
        this.appointments = response;

        console.log('Appointments:', this.appointments);

        // Find appointment using Bill ID

        const selectedAppointment = this.appointments.find(
          (appointment) => appointment.bill?.id === this.billId,
        );

        if (selectedAppointment) {
          this.appointment = selectedAppointment;

          this.bill.appointment = selectedAppointment;

          console.log('Bill Appointment:', this.appointment);
        } else {
          console.log('Appointment not found for Bill ID:', this.billId);
        }
        this.cdr.detectChanges();
      },

      error: (error) => {
        console.error('Error loading appointments:', error);
      },
    });
  }

  // --------------------------------
  // Print Bill
  // --------------------------------

  printBill(): void {
    window.print();
  }
}
