import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { MedicineEntity } from '../../../../model/medicine/medicine';
import { PrescriptionEntity } from '../../../../model/prescription/prescription';
import { PrescriptionService } from '../../../../service/prescriptionSerice/prescription-service';


@Component({
  selector: 'app-update-prescription',
  imports: [
    FormsModule,
    DatePipe,
    RouterLink

  ],
  templateUrl: './edit-prescription.html',
  styleUrl: './edit-prescription.css',
})
export class EditPrescription implements OnInit {

  prescriptionId!: number;

  prescription: PrescriptionEntity = {
    medicine: [],
    diagnosis: '',
    notes: '',
    prescriptionDate: '',
    appointment: null as any
  };

  loading = true;
  updating = false;

  constructor(
    private prescriptionService: PrescriptionService,
    private route: ActivatedRoute,
    private router: Router,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit(): void {

    this.prescriptionId = Number(
      this.route.snapshot.paramMap.get('id')
    );

    this.loadPrescription();
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

          this.loading = false;

          this.cdr.detectChanges();

          console.log(
            'Prescription:',
            this.prescription
          );

          console.log(
            'Appointment:',
            this.prescription.appointment
          );

          console.log(
            'Medicines:',
            this.prescription.medicine
          );
        },

        error: (error) => {

          console.error(
            'Error loading prescription:',
            error
          );

          this.loading = false;

          alert('Unable to load prescription.');

          this.router.navigate([
            '/dashboard/doctor/prescriptions'
          ]);
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

    if (!this.prescription.appointment?.id) {
      alert('Appointment information is missing.');
      return;
    }

    if (!this.prescription.diagnosis.trim()) {
      alert('Please enter diagnosis.');
      return;
    }

    if (!this.prescription.prescriptionDate) {
      alert('Please select prescription date.');
      return;
    }

    if (this.prescription.medicine.length === 0) {
      alert('Please add at least one medicine.');
      return;
    }

    this.updating = true;

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

          this.updating = false;

          alert('Prescription updated successfully!');

          this.router.navigate([
            '/dashboard/doctor/prescriptions'
          ]);
        },

        error: (error) => {

          console.error(
            'Error updating prescription:',
            error
          );

          this.updating = false;

          alert(
            error.error?.message ||
            'Failed to update prescription.'
          );
        }

      });
  }
}