import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { Router, RouterLink } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { AppointmentStatus } from '../../../enum/appointment-status';
import { AppointmentEntity } from '../../../model/appointment/appointment';
import { DoctorEntity } from '../../../model/doctor/doctor';
import { PatientEntity } from '../../../model/patient/patient-entity';
import { AppointmentService } from '../../../service/appointment/appointment-service';
import { DoctorService } from '../../../service/doctorService/doctor-service';
import { PatientService } from '../../../service/patient/patient-service';


@Component({
  selector: 'app-add-appointment',
  imports: [RouterLink, FormsModule],
  templateUrl: './add-appointment.html',
  styleUrl: './add-appointment.css',
})
export class AddAppointment implements OnInit {

  patients: PatientEntity[] = [];

  doctors: DoctorEntity[] = [];

  appointment: AppointmentEntity = {
    appointmentDate: '',
    appointmentTime: '',
    appointmentStatus: AppointmentStatus.BOOKED,
    reason: '',
    doctor: {} as DoctorEntity,
    patient: {} as PatientEntity
  };

  appointmentStatuses = Object.values(AppointmentStatus);

  constructor(
    private appointmentService: AppointmentService,
    private patientService: PatientService,
    private doctorService: DoctorService,
    private router: Router,
    private cdr:ChangeDetectorRef
  ) {}

  ngOnInit(): void {

    this.loadPatients();

    this.loadDoctors();

  }

  loadPatients(): void {

    this.patientService.getAllPatients().subscribe({
      next: (res) => {
        this.patients = res;
        this.cdr.detectChanges();
      },
      error: (err) => {
        console.error('Error loading patients:', err);
      }
    
    });

  }

  loadDoctors(): void {

    this.doctorService.getAllDoctors().subscribe({
      next: (res) => {
        this.doctors = res;
        this.cdr.detectChanges();

      },
      error: (err) => {
        console.error('Error loading doctors:', err);
      }
    });

  }

  createAppointment(): void {

    this.appointmentService
      .createAppointment(this.appointment)
      .subscribe({
        next: (res) => {

          
          alert('Appointment created successfully !')
          this.router.navigate(['/dashboard/admin/appointment']);

        },

        error: (err) => {

          alert('Appointment cannot created !');

        }
      });

  }

}