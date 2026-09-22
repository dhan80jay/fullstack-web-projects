import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { Router, RouterLink } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { DatePipe } from '@angular/common';

import { PrescriptionEntity } from '../../../model/prescription/prescription';
import { AppointmentService } from '../../../service/appointment/appointment-service';
import { PrescriptionService } from '../../../service/prescriptionSerice/prescription-service';

import { AppointmentEntity } from '../../../model/appointment/appointment';
import { MedicineEntity } from '../../../model/medicine/medicine';

@Component({
  selector: 'app-add-prescription',
  imports: [
    RouterLink,
    FormsModule,
    DatePipe
  ],
  templateUrl: './add-prescription.html',
  styleUrl: './add-prescription.css',
})
export class AddPrescription implements OnInit {

  appointments: AppointmentEntity[] = [];

  prescription: PrescriptionEntity = {
    medicine: [],
    diagnosis: '',
    notes: '',
    prescriptionDate: '',
    appointment: null as any
  };

  constructor(
    private prescriptionService: PrescriptionService,
    private appointmentService: AppointmentService,
    private router: Router,
    private cdr:ChangeDetectorRef
  ) {}

  // --------------------------------
  // Component initialization
  // --------------------------------

  ngOnInit(): void {
    this.loadAppointments();
    this.addMedicine();
  }

  // --------------------------------
  // Load appointments
  // --------------------------------

  loadAppointments(): void {

    this.appointmentService.getAllAppointments().subscribe({

      next: (response) => {

        this.appointments = response;

        this.cdr.detectChanges();
      },

      error: (error) => {

        console.error(
          'Error loading appointments:',
          error
        );

      }

    });

  }

  // --------------------------------
  // Select appointment
  // --------------------------------

  selectAppointment(appointment: AppointmentEntity): void {

    this.prescription.appointment = appointment;


  }

  // --------------------------------
  // Create empty medicine
  // --------------------------------

  createEmptyMedicine(): MedicineEntity {

    return {
      name: '',
      dosage: '',
      frequency: '',
      duration: '',
      notes: ''
    };

  }

  // --------------------------------
  // Add medicine
  // --------------------------------

  addMedicine(): void {

    this.prescription.medicine.push(
      this.createEmptyMedicine()
    );

  }

  // --------------------------------
  // Remove medicine
  // --------------------------------

  removeMedicine(index: number): void {

    // Keep at least one medicine row
    if (this.prescription.medicine.length <= 1) {
      return;
    }

    this.prescription.medicine.splice(index, 1);

  }

  // --------------------------------
  // Create prescription
  // --------------------------------

  createPrescription(): void {


    this.prescriptionService
      .createPrescription(this.prescription)
      .subscribe({

        next: (response) => {

          console.log(
            'Prescription created successfully:',
            response
          );

          alert('Prescription created successfully!');

          this.router.navigate([
            '/dashboard/admin/prescription'
          ]);

        },

        error: (error) => {

          console.error(
            'Error creating prescription:',
            error
          );

          alert('Failed to create prescription.');

        }

      });

  }

}