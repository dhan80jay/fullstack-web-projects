import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { PatientEntity } from '../../model/patient/patient-entity';
import { PatientService } from '../../service/patient/patient-service';
import { HealthInfo } from '../../model/patient/health-info';

@Component({
  selector: 'app-update-patient-by-patient',
  imports: [FormsModule, RouterLink],
  templateUrl: './update-patient.html',
  styleUrl: './update-patient.css',
})
export class UpdatePatientByPatient implements OnInit {

  patientId!: number;

  patient: HealthInfo = {
    dateOfBirth: '',
    bloodGroup: '',
    gender: '',
    phoneNumber: '',
  };

  constructor(
    private patientService: PatientService,
    private activatedRoute: ActivatedRoute,
    private router: Router,
    private cdr:ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    this.patientId = Number(
      this.activatedRoute.snapshot.paramMap.get('patientId')
    );

    this.loadPatient();
  }

  loadPatient(): void {
    this.patientService.getPatientById(this.patientId).subscribe({
      next: (response) => {
        this.patient = response;
      },
      error: (error) => {
        console.error('Error loading patient:', error);
        alert('Unable to load patient information.');
      }
    });
  }

  updateHealthInformation(): void {

    this.patientService
      .updateHealthInformation(this.patientId, this.patient)
      .subscribe({
        next: (response) => {
          console.log('Health information updated:', response);
          alert('Health information updated successfully!');
          window.location.reload();
          this.ngOnInit();
        },
        error: (error) => {
          console.error('Error updating health information:', error);
          alert(
            error.error?.message ||
            'Unable to update health information.'
          );
        }
      });
  }
}