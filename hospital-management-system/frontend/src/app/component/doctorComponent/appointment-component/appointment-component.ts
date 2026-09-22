import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { AppointmentStatus } from '../../../enum/appointment-status';
import { AppointmentEntity } from '../../../model/appointment/appointment';
import { AppointmentService } from '../../../service/appointment/appointment-service';
import { DoctorService } from '../../../service/doctorService/doctor-service';
import { NgxPaginationModule } from 'ngx-pagination';
import { CommonModule, DatePipe } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-appointment-component',
imports: [
  RouterLink,
  NgxPaginationModule,
  DatePipe,
  FormsModule,
  CommonModule
],  templateUrl: './appointment-component.html',
  styleUrl: './appointment-component.css',
})
export class AppointmentComponent implements OnInit{

  p: number = 1;
  AppointmentStatus = AppointmentStatus;

  appointments: AppointmentEntity[] = [];

  sortedAppointment: AppointmentEntity[] = [];

  pendingAppointments = 0;

  completedAppointments = 0;

  today = new Date();

  registeredToday = 0;

  searchText: string = '';

  sortAscending = true;

  constructor(
    private appointmentService: AppointmentService,
    private doctorService: DoctorService,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    this.loadDoctorAppointments();
  }

  loadDoctorAppointments(): void {

    this.doctorService.getLoggedInDoctor().subscribe({
      next: (doctor) => {

        if (!doctor.id) {
          console.error('Doctor ID not found');
          return;
        }

        this.doctorService.getAppointmentByDoctorId(doctor.id).subscribe({
          next: (response) => {

            this.appointments = response;

            this.sortedAppointment = [...response];

            this.calculateTodaysAppointments();
            this.calculatePendingAppointments();
            this.calculateCompletedAppointments();

            this.cdr.detectChanges();
          },

          error: (error) => {
            console.error('Error loading doctor appointments:', error);
          }
        });
      },

      error: (error) => {
        console.error('Error loading logged-in doctor:', error);
      }
    });
  }

  calculateTodaysAppointments(): void {

    this.registeredToday = this.appointments.filter((appointment) => {

      if (!appointment.appointmentDate) {
        return false;
      }

      const date = new Date(appointment.appointmentDate);

      return (
        date.getFullYear() === this.today.getFullYear() &&
        date.getMonth() === this.today.getMonth() &&
        date.getDate() === this.today.getDate()
      );

    }).length;
  }

  calculatePendingAppointments(): void {

    this.pendingAppointments = this.appointments.filter((appointment) => {

      return (
        appointment.appointmentStatus === AppointmentStatus.PENDING
      );

    }).length;
  }

  calculateCompletedAppointments(): void {

    this.completedAppointments = this.appointments.filter((appointment) => {

      return (
        appointment.appointmentStatus === AppointmentStatus.COMPLETED
      );

    }).length;
  }

  filterAppointmentByPatientName(): void {

    this.sortedAppointment.sort((a, b) => {

      const nameA =
        `${a.patient.firstName} ${a.patient.lastName}`.toLowerCase();

      const nameB =
        `${b.patient.firstName} ${b.patient.lastName}`.toLowerCase();

      const result = nameA.localeCompare(nameB);

      return this.sortAscending ? result : -result;
    });

    this.sortAscending = !this.sortAscending;
  }

  searchAppointmentByPatientName(): void {

    const search = this.searchText.trim().toLowerCase();

    if (search === '') {

      this.sortedAppointment = [...this.appointments];

    } else {

      this.sortedAppointment = this.appointments.filter((appointment) => {

        const firstName =
          appointment.patient.firstName?.toLowerCase() || '';

        const lastName =
          appointment.patient.lastName?.toLowerCase() || '';

        return (
          firstName.includes(search) ||
          lastName.includes(search)
        );
      });
    }

    this.p = 1;

    this.cdr.detectChanges();
  }

  updateAppointmentStatus(
    appointmentId: number | undefined,
    status: AppointmentStatus
  ): void {

    if (!appointmentId) {
      return;
    }

    this.doctorService.getLoggedInDoctor().subscribe({
      next: (doctor) => {

        if (!doctor.id) {
          return;
        }

        this.doctorService.updateAppointmentStatus(
          doctor.id,
          appointmentId,
          status
        ).subscribe({
          next: () => {

            const appointment = this.appointments.find(
              appointment => appointment.id === appointmentId
            );

            if (appointment) {
              appointment.appointmentStatus = status;
            }

            this.calculatePendingAppointments();
            this.calculateCompletedAppointments();

            this.cdr.detectChanges();
          },

          error: (error) => {
            console.error(
              'Error updating appointment status:',
              error
            );
          }
        });
      },

      error: (error) => {
        console.error(
          'Error loading logged-in doctor:',
          error
        );
      }
    });
  }

  markCompleted(appointmentId: number | undefined): void {

    this.updateAppointmentStatus(
      appointmentId,
      AppointmentStatus.COMPLETED
    );
  }

  markCancelled(appointmentId: number | undefined): void {

    this.updateAppointmentStatus(
      appointmentId,
      AppointmentStatus.CANCELLED
    );
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

  getStatusClass(status: AppointmentStatus): string {

    switch (status) {

      case AppointmentStatus.PENDING:
        return 'pending';

      case AppointmentStatus.COMPLETED:
        return 'completed';

      case AppointmentStatus.CANCELLED:
        return 'cancelled';

      case AppointmentStatus.BOOKED:
        return 'confirmed';

      default:
        return '';
    }
  }
}
