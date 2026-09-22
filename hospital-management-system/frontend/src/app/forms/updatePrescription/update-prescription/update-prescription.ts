import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { FormsModule } from '@angular/forms';

import { PrescriptionEntity } from '../../../model/prescription/prescription';
import { AppointmentEntity } from '../../../model/appointment/appointment';
import { MedicineEntity } from '../../../model/medicine/medicine';

import { PrescriptionService } from '../../../service/prescriptionSerice/prescription-service';
import { AppointmentService } from '../../../service/appointment/appointment-service';

@Component({
  selector: 'app-update-prescription',
  imports: [
    RouterLink,
    FormsModule,
    DatePipe
  ],
  templateUrl: './update-prescription.html',
  styleUrl: './update-prescription.css',
})
export class UpdatePrescription implements OnInit {

  prescriptionId!: number;

  selectedAppointmentId!: number;

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
    private route: ActivatedRoute,
    private router: Router,
    private cdr:ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    this.prescriptionId = Number(
      this.route.snapshot.paramMap.get('id')
    );

    this.loadAppointments();
    this.loadPrescription();
  }

  loadAppointments(): void {
    this.appointmentService.getAllAppointments().subscribe({
      next: (response) => {
        this.appointments = response;

        if (this.prescription.appointment?.id) {
          this.selectedAppointmentId = this.prescription.appointment.id;
        }
      },
      error: (error) => {
        console.error('Error loading appointments:', error);
      }
    });
  }

loadPrescription(): void {

  this.prescriptionService
    .getPrescriptionForUpdate(this.prescriptionId)
    .subscribe({

      next: (response) => {

        this.prescription = {
          id: response.id,
          diagnosis: response.diagnosis,
          notes: response.notes,
          prescriptionDate: response.prescriptionDate,
          appointment: response.appointment,
          medicine: response.medicine || []
        };

        this.selectedAppointmentId =
          response.appointment?.id;

        console.log(
          'Prescription:',
          this.prescription
        );

        console.log(
          'Selected Appointment ID:',
          this.selectedAppointmentId
        );

        console.log(
          'Medicines:',
          this.prescription.medicine
        );

        console.log(
          'Medicine Count:',
          this.prescription.medicine.length
        );

        this.cdr.detectChanges();

      },

      error: (error) => {

        console.error(
          'Error loading prescription:',
          error
        );

        alert('Unable to load prescription.');

      }

    });

}
  createEmptyMedicine(): MedicineEntity {
    return {
      name: '',
      dosage: '',
      frequency: '',
      duration: '',
      notes: ''
    };
  }

  addMedicine(): void {
    this.prescription.medicine.push(
      this.createEmptyMedicine()
    );
  }

  removeMedicine(index: number): void {
    if (this.prescription.medicine.length <= 1) {
      return;
    }

    this.prescription.medicine.splice(index, 1);
  }

  updatePrescription(): void {

    const selectedAppointment = this.appointments.find(
      appointment => appointment.id === this.selectedAppointmentId
    );

    if (!selectedAppointment) {
      alert('Please select an appointment.');
      return;
    }

    this.prescription.appointment = selectedAppointment;

    console.log('Prescription before update:', this.prescription);

    this.prescriptionService
      .updatePrescription(
        this.prescriptionId,
        this.prescription
      )
      .subscribe({
        next: (response) => {
          console.log(
            'Prescription updated successfully:',
            response
          );

          alert('Prescription updated successfully!');

          this.router.navigate([
            '/dashboard/admin/prescription'
          ]);
        },
        error: (error) => {
          console.error(
            'Error updating prescription:',
            error
          );

          alert(
            error.error?.message ||
            'Failed to update prescription.'
          );
        }
      });
  }
}