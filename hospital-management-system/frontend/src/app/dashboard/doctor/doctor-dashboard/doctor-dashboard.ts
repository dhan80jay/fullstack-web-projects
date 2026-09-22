import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { RouterLink, RouterOutlet } from '@angular/router';

 
import { DoctorEntity } from '../../../model/doctor/doctor';
import { AppointmentEntity } from '../../../model/appointment/appointment';
import { DoctorService } from '../../../service/doctorService/doctor-service';
import { AuthService } from '../../../service/authService/auth-service';

@Component({
  selector: 'app-doctor-dashboard',
  imports: [RouterLink, DatePipe,RouterOutlet],
  templateUrl: './doctor-dashboard.html',
  styleUrl: './doctor-dashboard.css',
})
export class DoctorDashboard implements OnInit {

  doctor!: DoctorEntity;

  appointments: AppointmentEntity[] = [];

  totalPatients = 0;
  todayAppointments = 0;
  completedToday = 0;
  pendingToday = 0;
  completedAppointments = 0;
  upcomingAppointments: AppointmentEntity[] = [];

  constructor(
    private doctorService: DoctorService,
    private cdr:ChangeDetectorRef,
    private authService:AuthService
  ) {}


  logout(){
    this.authService.logout();
  }

  getDoctorAndAppointments(): void {

    this.doctorService.getLoggedInDoctor().subscribe({

      next: (doctor) => {

        this.doctor = doctor;

        console.log('Logged in doctor:', this.doctor);

        if (this.doctor.id) {

          this.doctorService
            .getAppointmentByDoctorId(this.doctor.id)
            .subscribe({

              next: (appointments) => {

                this.appointments = appointments;

                console.log(
                  'Doctor appointments:',
                  this.appointments
                );

                this.calculateDashboardData();
                this.cdr.detectChanges();
              },

              error: (error) => {

                console.error(
                  'Error loading doctor appointments:',
                  error
                );

              }

            });

        }

      },

      error: (error) => {

        console.error(
          'Error loading logged-in doctor:',
          error
        );

      }

    });
  }


  calculateDashboardData(): void {

    const today = new Date();

    const todayString =
      `${today.getFullYear()}-${String(
        today.getMonth() + 1
      ).padStart(2, '0')}-${String(
        today.getDate()
      ).padStart(2, '0')}`;

    const todayAppointments =
      this.appointments.filter(
        appointment =>
          appointment.appointmentDate === todayString
      );

    this.todayAppointments =
      todayAppointments.length;
    
    this.completedAppointments = this.appointments.filter(
      appointment => appointment.appointmentStatus === 'COMPLETED'
    ).length;

    this.completedToday =
      todayAppointments.filter(
        appointment =>
          appointment.appointmentStatus === 'COMPLETED'
      ).length;
      this.cdr.detectChanges();
    this.pendingToday =
      todayAppointments.filter(
        appointment =>
          appointment.appointmentStatus === 'BOOKED'
      ).length;
      this.cdr.detectChanges();

    const patientIds = new Set(
      this.appointments
        .filter(
          appointment => appointment.patient?.id
        )
        .map(
          appointment => appointment.patient.id
        )
    );
      this.cdr.detectChanges();

    this.totalPatients = patientIds.size;

    const now = new Date();

    this.upcomingAppointments =
      this.appointments
        .filter(appointment => {

          if (
            appointment.appointmentStatus !== 'BOOKED'
          ) {
            return false;
          }

          const appointmentDateTime =
            new Date(
              `${appointment.appointmentDate}T${appointment.appointmentTime}`
            );

          return appointmentDateTime > now;

        })
        .sort((a, b) => {

          const dateA =
            new Date(
              `${a.appointmentDate}T${a.appointmentTime}`
            ).getTime();

          const dateB =
            new Date(
              `${b.appointmentDate}T${b.appointmentTime}`
            ).getTime();

          return dateA - dateB;

        })
        .slice(0, 3);
        this.cdr.detectChanges();
  }


  getTodayAppointments(): AppointmentEntity[] {

    const today = new Date();

    const todayString =
      `${today.getFullYear()}-${String(
        today.getMonth() + 1
      ).padStart(2, '0')}-${String(
        today.getDate()
      ).padStart(2, '0')}`;

    return this.appointments
      .filter(
        appointment =>
          appointment.appointmentDate === todayString
      )
      .sort((a, b) =>
        a.appointmentTime.localeCompare(
          b.appointmentTime
        )
        
      );
  }


  getDoctorPatients(): any[] {

    const patients =
      this.appointments
        .map(
          appointment => appointment.patient
        )
        .filter(
          patient => patient != null
        );

    return Array.from(
      new Map(
        patients.map(
          patient => [patient.id, patient]


        )
      ).values()
    );
    
  }


  getDoctorPrescriptions(): any[] {

    return this.appointments
      .filter(
        appointment => appointment.prescription
      )
      .map(
        appointment => appointment.prescription
      );
  }


  getPatientInitials(patient: any): string {

    if (!patient) {
      return '';
    }

    return `${patient.firstName?.charAt(0) || ''}${patient.lastName?.charAt(0) || ''}`;
  }


  getDoctorInitials(): string {

    if (!this.doctor) {
      return '';
    }

    return `${this.doctor.firstName?.charAt(0) || ''}${this.doctor.lastName?.charAt(0) || ''}`;
  }


  formatTime(time?: string): string {

    if (!time) {
      return '';
    }

    const [hours, minutes] =
      time.split(':');

    const hour = Number(hours);

    const ampm =
      hour >= 12 ? 'PM' : 'AM';

    const displayHour =
      hour % 12 || 12;

    return `${displayHour}:${minutes} ${ampm}`;
  }


  scrollToSection(sectionId: string): void {

    const element =
      window.document.getElementById(sectionId);

    if (element) {

      element.scrollIntoView({
        behavior: 'smooth',
        block: 'start'
      });

    }
  }


  ngOnInit(): void {

    this.getDoctorAndAppointments();

  }
}