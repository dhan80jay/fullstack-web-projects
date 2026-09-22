import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { PatientService } from '../../service/patient/patient-service';
import { AppointmentService } from '../../service/appointment/appointment-service';
import { PatientEntity } from '../../model/patient/patient-entity';
import { RouterLink, RouterOutlet } from '@angular/router';
import { AppointmentEntity } from '../../model/appointment/appointment';
import { DatePipe } from '@angular/common';
import { NgxPaginationModule } from 'ngx-pagination';
import { PrescriptionService } from '../../service/prescriptionSerice/prescription-service';
import { BillService } from '../../service/billService/bill-service';
import { BillEntity } from '../../model/bill/bill';
@Component({
  selector: 'app-patient',
  imports: [RouterLink, DatePipe, RouterOutlet, NgxPaginationModule],
  templateUrl: './patientDashboard.html',
  styleUrl: './patientDashboard.css',
})
export class PatientDashboard implements OnInit {
  patient!: PatientEntity;
  patientId: any;

  appointmentPage: number = 1;
  prescriptionPage: number = 1;
  prescriptionMedicines: any[] = [];
  appointments: AppointmentEntity[] = [];
billPage: number = 1;
bills: BillEntity[] = [];
  upcomingAppointmentDays: number | null = null;

  upcomingAppointment: number = 0;

  upcomingAppointmentInfo!: AppointmentEntity | null;

constructor(
  private patientService: PatientService,
  private appointmentService: AppointmentService,
  private prescriptionService: PrescriptionService,
  private billService: BillService,
  private cdr: ChangeDetectorRef,
) {}

  getPatientAndAppointment() {
    this.patientService.getPatientByUsername().subscribe((res) => {
      this.patient = res;
      this.patientId = res.id;

      this.billService.getBillsByPatient().subscribe({
      next: (bills) => {
        this.bills = bills;
        console.log(bills);
        
        this.billPage = 1;
        this.cdr.detectChanges();
      },
      error: (error) => {
        console.error('Error loading patient bills:', error);
      }
    });


      this.appointmentService.getAppointmentsByPatientId(this.patientId).subscribe((res) => {
        this.appointments = res;

        this.loadPrescriptionMedicines();

        this.getUpcomingAppointmentDays();
        this.upcomingAppointments();
        this.upcomingAppointmentInformation();

        this.cdr.detectChanges();
      });
    });
  }

scrollToSection(sectionId: string): void {
  const element = window.document.getElementById(sectionId);

  if (element) {
    element.scrollIntoView({
      behavior: 'smooth',
      block: 'start'
    });
  }
}

  loadPrescriptionMedicines(): void {
    this.prescriptionMedicines = [];

    this.appointments.forEach((appointment) => {
      if (appointment.prescription?.id) {
        this.prescriptionService
          .getMedicinesByPrescriptionId(appointment.prescription.id)
          .subscribe({
            next: (medicines) => {
              medicines.forEach((medicine) => {
                this.prescriptionMedicines.push({
                  medicine: medicine,
                  prescription: appointment.prescription,
                });
              });

              this.cdr.detectChanges();
            },

            error: (error) => {
              console.error('Error loading medicines for prescription:', error);
            },
          });
      }
    });
  }

  getPendingBillCount(): number {
    return this.appointments.filter(
      (appointment) => appointment.bill && appointment.bill.paymentStatus === 'PENDING',
    ).length;
  }

  getPendingBillAmount(): number {
    return this.appointments
      .filter((appointment) => appointment.bill && appointment.bill.paymentStatus === 'PENDING')
      .reduce((total, appointment) => total + appointment.bill!.totalAmount, 0);
  }

  getActivePrescriptionCount(): number {
    return this.appointments.filter((appointment) => appointment.prescription).length;
  }

  getUpcomingAppointmentDays(): void {
    const now = new Date();

    const upcomingAppointments = this.appointments
      .filter((appointment) => {
        const appointmentDateTime = new Date(
          `${appointment.appointmentDate}T${appointment.appointmentTime}`,
        );

        return appointmentDateTime > now && appointment.appointmentStatus === 'BOOKED';
      })
      .sort((a, b) => {
        const dateA = new Date(`${a.appointmentDate}T${a.appointmentTime}`).getTime();
        const dateB = new Date(`${b.appointmentDate}T${b.appointmentTime}`).getTime();

        return dateA - dateB;
      });

    if (upcomingAppointments.length === 0) {
      this.upcomingAppointmentDays = null;
      return;
    }

    const today = new Date();
    const appointmentDate = new Date(upcomingAppointments[0].appointmentDate);

    today.setHours(0, 0, 0, 0);
    appointmentDate.setHours(0, 0, 0, 0);

    const difference = appointmentDate.getTime() - today.getTime();

    this.upcomingAppointmentDays = Math.ceil(difference / (1000 * 60 * 60 * 24));
  }

  upcomingAppointments() {
    const now = new Date();

    this.upcomingAppointment = this.appointments.filter((appointment) => {
      const appointmentDateTime = new Date(
        `${appointment.appointmentDate}T${appointment.appointmentTime}`,
      );

      return appointmentDateTime > now && appointment.appointmentStatus === 'BOOKED';
    }).length;
  }

  upcomingAppointmentInformation() {
    const now = new Date();

    const upcoming = this.appointments
      .filter((appointment) => {
        const appointmentDateTime = new Date(
          `${appointment.appointmentDate}T${appointment.appointmentTime}`,
        );

        return appointmentDateTime > now && appointment.appointmentStatus === 'BOOKED';
      })
      .sort((a, b) => {
        const dateA = new Date(`${a.appointmentDate}T${a.appointmentTime}`).getTime();

        const dateB = new Date(`${b.appointmentDate}T${b.appointmentTime}`).getTime();

        return dateA - dateB;
      });

    this.upcomingAppointmentInfo = upcoming.length > 0 ? upcoming[0] : null;
  }

  cancelAppointment() {
    if (!this.upcomingAppointmentInfo) {
      return;
    }

    this.appointmentService.cancelAppointment(this.upcomingAppointmentInfo.id!).subscribe({
      next: () => {
        alert('Appointment cancelled successfully!');
        this.getPatientAndAppointment();
      },
      error: (error) => {
        console.error('Error cancelling appointment:', error);
        alert('Unable to cancel appointment.');
      },
    });
  }

  formatTime(time?: string): string {
    if (!time) {
      return '';
    }

    const [hours, minutes] = time.split(':');

    const hour = Number(hours);
    const ampm = hour >= 12 ? 'PM' : 'AM';
    const displayHour = hour % 12 || 12;

    return `${displayHour}:${minutes} ${ampm}`;
  }

  getRecentAppointments(): AppointmentEntity[] {
    return [...this.appointments]
      .sort((a, b) => {
        const dateA = new Date(`${a.appointmentDate}T${a.appointmentTime}`).getTime();

        const dateB = new Date(`${b.appointmentDate}T${b.appointmentTime}`).getTime();

        return dateB - dateA;
      })
      .slice(0, 3);
  }

  getCurrentPrescriptions(): any[] {
    return this.prescriptionMedicines;
  }
  ngOnInit(): void {
    this.getPatientAndAppointment();
  }
}
