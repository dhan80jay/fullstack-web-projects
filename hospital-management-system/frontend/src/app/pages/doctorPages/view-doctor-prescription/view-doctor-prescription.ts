import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { DatePipe } from '@angular/common';

import { PrescriptionService } from '../../../service/prescriptionSerice/prescription-service';

import { PrescriptionEntity } from '../../../model/prescription/prescription';
import { AppointmentEntity } from '../../../model/appointment/appointment';
import { MedicineEntity } from '../../../model/medicine/medicine';
import { DoctorService } from '../../../service/doctorService/doctor-service';

@Component({
  selector: 'app-view-prescription',
  imports: [RouterLink, DatePipe],
  templateUrl: './view-doctor-prescription.html',
  styleUrl: './view-doctor-prescription.css',
})
export class ViewDoctorPrescription implements OnInit {

  prescriptionId!: number;

  prescription!: PrescriptionEntity;
  appointment!: AppointmentEntity;
  medicines: MedicineEntity[] = [];

  loading = true;

  constructor(
    private prescriptionService: PrescriptionService,
    private doctorService: DoctorService,
    private activatedRoute: ActivatedRoute,
    private cdr: ChangeDetectorRef
  ) {}

  loadPrescription(): void {

    this.activatedRoute.paramMap.subscribe(params => {

      const id = params.get('prescriptionId');

      if (!id) {
        this.loading = false;
        return;
      }

      this.prescriptionId = Number(id);

      this.prescriptionService
        .getPrescriptionById(this.prescriptionId)
        .subscribe({
          next: (prescription) => {

            this.prescription = prescription;

            this.loadMedicines();

            this.loadAppointment();

            this.cdr.detectChanges();
          },

          error: (error) => {

            console.error('Error loading prescription:', error);

            this.loading = false;

            this.cdr.detectChanges();
          }
        });

    });
  }

  loadMedicines(): void {

    this.prescriptionService
      .getMedicinesByPrescriptionId(this.prescriptionId)
      .subscribe({
        next: (medicines) => {

          this.medicines = medicines;

          this.cdr.detectChanges();
        },

        error: (error) => {

          console.error('Error loading medicines:', error);

          this.medicines = [];

          this.cdr.detectChanges();
        }
      });
  }

  loadAppointment(): void {

    this.doctorService
      .getLoggedInDoctor()
      .subscribe({
        next: (doctor) => {

          if (!doctor.id) {
            this.loading = false;
            return;
          }

          this.doctorService
            .getAppointmentByDoctorId(doctor.id)
            .subscribe({
              next: (appointments) => {

                const foundAppointment = appointments.find(
                  appointment =>
                    appointment.prescription?.id === this.prescriptionId
                );

                if (foundAppointment) {
                  this.appointment = foundAppointment;
                }

                this.loading = false;

                this.cdr.detectChanges();
              },

              error: (error) => {

                console.error(
                  'Error loading doctor appointments:',
                  error
                );

                this.loading = false;

                this.cdr.detectChanges();
              }
            });
        },

        error: (error) => {

          console.error(
            'Error loading logged-in doctor:',
            error
          );

          this.loading = false;

          this.cdr.detectChanges();
        }
      });
  }

  printPrescription(): void {
    window.print();
  }

  ngOnInit(): void {
    this.loadPrescription();
  }
}