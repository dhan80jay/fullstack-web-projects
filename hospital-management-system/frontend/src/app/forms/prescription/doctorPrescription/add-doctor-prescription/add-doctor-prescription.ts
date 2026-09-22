import { DatePipe } from '@angular/common';
import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { RouterLink, Router } from '@angular/router';
import { AppointmentEntity } from '../../../../model/appointment/appointment';
import { DoctorEntity } from '../../../../model/doctor/doctor';
import { MedicineEntity } from '../../../../model/medicine/medicine';
import { PrescriptionEntity } from '../../../../model/prescription/prescription';
import { DoctorService } from '../../../../service/doctorService/doctor-service';
import { PrescriptionService } from '../../../../service/prescriptionSerice/prescription-service';

@Component({
  selector: 'app-add-doctor-prescription',
  imports: [FormsModule,RouterLink,DatePipe],
  templateUrl: './add-doctor-prescription.html',
  styleUrl: './add-doctor-prescription.css',
})
export class AddDoctorPrescription implements OnInit{

  doctor!: DoctorEntity;

  appointments: AppointmentEntity[] = [];

  prescription: PrescriptionEntity = {
    diagnosis: '',
    notes: '',
    prescriptionDate: '',
    appointment: null as any,
    medicine: []
  };

  loading = true;
  saving = false;

  constructor(
    private doctorService: DoctorService,
    private prescriptionService: PrescriptionService,
    private router: Router,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    this.loadDoctor();
  }

  loadDoctor(): void {

    this.doctorService.getLoggedInDoctor().subscribe({

      next: (doctor) => {

        this.doctor = doctor;

        if (!doctor.id) {
          this.loading = false;
          return;
        }

        this.loadAppointments(doctor.id);

      },

      error: (error) => {

        console.error('Error loading doctor:', error);

        this.loading = false;

        alert('Unable to load doctor information.');

        this.router.navigate(['/dashboard/doctor/prescriptions']);

      }

    });

  }

  loadAppointments(doctorId: number): void {

    this.doctorService.getAppointmentByDoctorId(doctorId).subscribe({

      next: (appointments) => {

        this.appointments = appointments.filter(
          appointment =>
            appointment.appointmentStatus === 'COMPLETED' &&
            !appointment.prescription
        );

        this.loading = false;

        this.cdr.detectChanges();

      },

      error: (error) => {

        console.error('Error loading appointments:', error);

        this.loading = false;

        alert('Unable to load appointments.');

        this.cdr.detectChanges();

      }

    });

  }

  onAppointmentChange(): void {

    if (!this.prescription.appointment) {
      this.prescription.appointment = null as any;
    }

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

  createPrescription(): void {

    if (!this.prescription.appointment?.id) {

      alert('Please select an appointment.');

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

    const invalidMedicine = this.prescription.medicine.some(
      medicine =>
        !medicine.name?.trim() ||
        !medicine.dosage?.trim() ||
        !medicine.frequency?.trim() ||
        !medicine.duration?.trim()
    );

    if (invalidMedicine) {

      alert(
        'Please fill Name, Dosage, Frequency and Duration for all medicines.'
      );

      return;

    }

    this.saving = true;

    this.prescriptionService
      .createPrescription(this.prescription)
      .subscribe({

        next: () => {

          this.saving = false;

          alert('Prescription created successfully!');

          this.router.navigate([
            '/dashboard/doctor/prescriptions'
          ]);

        },

        error: (error) => {

          console.error(
            'Error creating prescription:',
            error
          );

          this.saving = false;

          alert(
            error.error?.message ||
            'Failed to create prescription.'
          );

          this.cdr.detectChanges();

        }

      });

  }


}
