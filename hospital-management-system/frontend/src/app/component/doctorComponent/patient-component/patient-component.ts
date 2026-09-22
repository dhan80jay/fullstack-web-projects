import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { NgxPaginationModule } from 'ngx-pagination';
import { PatientEntity } from '../../../model/patient/patient-entity';
import { AppointmentEntity } from '../../../model/appointment/appointment';
import { DoctorService } from '../../../service/doctorService/doctor-service';


@Component({
  selector: 'app-patient',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    RouterLink,
    NgxPaginationModule
  ],
  templateUrl: './patient-component.html',
  styleUrl: './patient-component.css'
})
export class PatientComponent implements OnInit {

  p = 1;

  patients: PatientEntity[] = [];
  sortedPatients: PatientEntity[] = [];

  malePatients = 0;
  femalePatients = 0;
  registeredToday = 0;

  today = new Date();

  searchText = '';
  ascending = true;
sortAscending = true;
  constructor(
    private doctorService: DoctorService,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    this.loadDoctorPatients();
  }

  loadDoctorPatients(): void {

    this.doctorService.getLoggedInDoctor().subscribe({
      next: (doctor) => {

        if (!doctor.id) {
          console.error('Doctor ID not found');
          return;
        }

        this.doctorService.getAppointmentByDoctorId(doctor.id).subscribe({
          next: (appointments: AppointmentEntity[]) => {

            const patientMap = new Map<number, PatientEntity>();

            appointments.forEach((appointment) => {

              if (appointment.patient?.id) {
                patientMap.set(
                  appointment.patient.id,
                  appointment.patient
                );
              }

            });

            this.patients = Array.from(patientMap.values());
            this.sortedPatients = [...this.patients];

            this.calculateGenderCount();
            this.calculateRegisteredToday();

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

  calculateGenderCount(): void {

    this.malePatients = this.patients.filter(
      patient => patient.gender?.toLowerCase() === 'male'
    ).length;

    this.femalePatients = this.patients.filter(
      patient => patient.gender?.toLowerCase() === 'female'
    ).length;
  }

  calculateRegisteredToday(): void {

    const todayDate = new Date();

    this.registeredToday = this.patients.filter(patient => {

      if (!patient.registeredAt) {
        return false;
      }

      const registeredDate = new Date(patient.registeredAt);

      return (
        registeredDate.getDate() === todayDate.getDate() &&
        registeredDate.getMonth() === todayDate.getMonth() &&
        registeredDate.getFullYear() === todayDate.getFullYear()
      );

    }).length;
  }

  searchPatientByName(): void {

    const search = this.searchText.trim().toLowerCase();

    if (!search) {
      this.sortedPatients = [...this.patients];
      return;
    }

    this.sortedPatients = this.patients.filter(patient => {

      const firstName = patient.firstName?.toLowerCase() || '';
      const lastName = patient.lastName?.toLowerCase() || '';

      return (
        firstName.includes(search) ||
        lastName.includes(search) ||
        `${firstName} ${lastName}`.includes(search)
      );
    });

    this.p = 1;
  }

filterPatientsByNames(): void {

  this.sortedPatients.sort((a, b) => {

    const nameA =
      `${a.firstName} ${a.lastName}`.toLowerCase();

    const nameB =
      `${b.firstName} ${b.lastName}`.toLowerCase();

    return this.sortAscending
      ? nameA.localeCompare(nameB)
      : nameB.localeCompare(nameA);
  });

  this.sortAscending = !this.sortAscending;
}


  get totalPatients(): number {
    return this.patients.length;
  }
}