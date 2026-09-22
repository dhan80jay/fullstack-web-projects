import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { CommonModule, DatePipe } from '@angular/common';
import { RouterLink } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { NgxPaginationModule } from 'ngx-pagination';
import { AppointmentEntity } from '../../../model/appointment/appointment';
import { PrescriptionEntity } from '../../../model/prescription/prescription';
import { DoctorService } from '../../../service/doctorService/doctor-service';


@Component({
  selector: 'app-prescription',
  standalone: true,
  imports: [
    CommonModule,
    RouterLink,
    FormsModule,
    DatePipe,
    NgxPaginationModule
  ],
  templateUrl: './prescription-component.html',
  styleUrl: './prescription-component.css',
})
export class PrescriptionComponent implements OnInit {

  p: number = 1;

  prescriptions: PrescriptionEntity[] = [];

  sortedPrescription: PrescriptionEntity[] = [];

  today = new Date();

  registeredToday = 0;

  searchText: string = '';

  sortAscending = true;

  constructor(
    private doctorService: DoctorService,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    this.loadDoctorPrescriptions();
  }

loadDoctorPrescriptions(): void {

  this.doctorService.getLoggedInDoctor().subscribe({
    next: (doctor) => {

      if (!doctor.id) {
        console.error('Doctor ID not found');
        return;
      }

      this.doctorService
        .getAppointmentByDoctorId(doctor.id)
        .subscribe({
          next: (appointments: AppointmentEntity[]) => {

            const doctorPrescriptions: PrescriptionEntity[] = [];

            appointments.forEach((appointment) => {

              if (appointment.prescription?.id) {

                const prescription: PrescriptionEntity = {
                  ...appointment.prescription,
                  appointment: appointment
                };

                doctorPrescriptions.push(prescription);
              }

            });

            this.prescriptions = doctorPrescriptions;
            this.sortedPrescription = [...this.prescriptions];

            this.todaysPrescription();

            console.log(
              'Doctor prescriptions:',
              this.prescriptions
            );

            this.cdr.detectChanges();
          },

          error: (error) => {
            console.error(
              'Error loading doctor appointments:',
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
  todaysPrescription(): void {

    this.registeredToday = this.prescriptions.filter(
      (prescription) => {

        if (!prescription.prescriptionDate) {
          return false;
        }

        const date = new Date(
          prescription.prescriptionDate
        );

        return (
          date.getFullYear() === this.today.getFullYear() &&
          date.getMonth() === this.today.getMonth() &&
          date.getDate() === this.today.getDate()
        );
      }
    ).length;
  }

  searchPatientByName(): void {

    const search = this.searchText.trim().toLowerCase();

    if (search === '') {

      this.sortedPrescription = [
        ...this.prescriptions
      ];

    } else {

      this.sortedPrescription =
        this.prescriptions.filter((prescription) => {

          const patient =
            prescription.appointment?.patient;

          if (!patient) {
            return false;
          }

          const firstName =
            patient.firstName?.toLowerCase() || '';

          const lastName =
            patient.lastName?.toLowerCase() || '';

          return (
            firstName.includes(search) ||
            lastName.includes(search) ||
            `${firstName} ${lastName}`.includes(search)
          );
        });
    }

    this.p = 1;

    this.cdr.detectChanges();
  }

  filterPatientsByNames(): void {

    this.sortedPrescription.sort((a, b) => {

      const nameA =
        `${a.appointment?.patient?.firstName || ''} ${a.appointment?.patient?.lastName || ''}`
          .toLowerCase();

      const nameB =
        `${b.appointment?.patient?.firstName || ''} ${b.appointment?.patient?.lastName || ''}`
          .toLowerCase();

      const result =
        nameA.localeCompare(nameB);

      return this.sortAscending
        ? result
        : -result;
    });

    this.sortAscending = !this.sortAscending;
  }
}