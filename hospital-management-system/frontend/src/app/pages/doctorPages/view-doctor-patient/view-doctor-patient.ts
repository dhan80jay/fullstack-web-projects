import { CommonModule, DatePipe } from '@angular/common';
import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { AppointmentEntity } from '../../../model/appointment/appointment';
import { PatientEntity } from '../../../model/patient/patient-entity';
import { DoctorService } from '../../../service/doctorService/doctor-service';
import { PatientService } from '../../../service/patient/patient-service';

@Component({
  selector: 'app-view-doctor-patient',
  imports: [DatePipe,CommonModule,RouterLink],
  templateUrl: './view-doctor-patient.html',
  styleUrl: './view-doctor-patient.css',
})
export class ViewDoctorPatient implements OnInit{

  patient: PatientEntity | null = null;

  appointments: AppointmentEntity[] = [];

  patientId!: number;

  loading = true;

  constructor(
    private route: ActivatedRoute,
    private patientService: PatientService,
    private doctorService: DoctorService,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit(): void {

    this.route.paramMap.subscribe(params => {

      const id = params.get('patientId');

      if (id) {
        this.patientId = Number(id);
        this.loadPatient();
      }

    });
  }

  loadPatient(): void {

    this.loading = true;

    this.patientService.getPatientById(this.patientId).subscribe({
      next: (patient) => {

        this.patient = patient;

        this.loadDoctorAppointments();

        this.cdr.detectChanges();
      },

      error: (error) => {

        console.error(
          'Error loading patient:',
          error
        );

        this.loading = false;
      }
    });
  }

  loadDoctorAppointments(): void {

    this.doctorService.getLoggedInDoctor().subscribe({
      next: (doctor) => {

        if (!doctor.id) {
          this.loading = false;
          return;
        }

        this.doctorService
          .getAppointmentByDoctorId(doctor.id)
          .subscribe({
            next: (appointments) => {

              this.appointments = appointments.filter(
                appointment =>
                  appointment.patient?.id === this.patientId
              );

              this.loading = false;

              this.cdr.detectChanges();
            },

            error: (error) => {

              console.error(
                'Error loading doctor appointments:',
                error
              );

              this.loading = false;
            }
          });
      },

      error: (error) => {

        console.error(
          'Error loading logged-in doctor:',
          error
        );

        this.loading = false;
      }
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

  getStatusClass(status: string): string {

    switch (status) {

      case 'PENDING':
        return 'pending';

      case 'BOOKED':
        return 'confirmed';

      case 'COMPLETED':
        return 'completed';

      case 'CANCELLED':
        return 'cancelled';

      default:
        return '';
    }
  }

}
