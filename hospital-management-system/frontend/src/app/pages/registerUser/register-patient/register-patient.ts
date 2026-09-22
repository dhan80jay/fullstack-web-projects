import { Component } from '@angular/core';
import { Router, RouterLink } from '@angular/router';
import { FormsModule } from '@angular/forms';

import { PatientService } from '../../../service/patient/patient-service';
import { PatientEntity } from '../../../model/patient/patient-entity';

@Component({
  selector: 'app-register-patient',
  imports: [RouterLink, FormsModule],
  templateUrl: './register-patient.html',
  styleUrl: './register-patient.css',
})
export class RegisterPatient {

  constructor(
    private patientService: PatientService,
    private router: Router
  ) {}

  patient: PatientEntity = {
    firstName: '',
    lastName: '',
    dateOfBirth: '',
    bloodGroup: '',
    email: '',
    gender: '',
    phoneNumber: '',
    address: '',
    user: {
      username: '',
      password: ''
    },
    appointments: []
  };

  registerPatient(): void {

    this.patientService.addPatient(this.patient).subscribe({

      next: (response) => {
        console.log('Patient registered successfully:', response);

        alert('Patient registered successfully!');

        this.router.navigate(['/login']);
      },

      error: (error) => {
        console.error('Patient registration failed:', error);

        alert(
          error.error?.message ||
          'Patient registration failed!'
        );
      }

    });

  }

}