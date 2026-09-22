import { Component } from '@angular/core';
import { RouterLink, Router } from '@angular/router';
import { FormsModule } from '@angular/forms';

import { DoctorService } from '../../../service/doctorService/doctor-service';
import { DoctorEntity } from '../../../model/doctor/doctor';

@Component({
  selector: 'app-register-doctor',
  imports: [RouterLink,FormsModule],
  templateUrl: './register-doctor.html',
  styleUrl: './register-doctor.css',
})
export class RegisterDoctor {

  constructor(
    private doctorService: DoctorService,
    private router: Router
  ) {}

  doctor: DoctorEntity = {

    firstName: '',

    lastName: '',

    specialization: '',

    email: '',

    phoneNumber: '',

    qualification: '',

    experienceYears: 0,

    user: {
      username: '',
      password: '',
    },

    appointments: [],

  };


  registerDoctor(): void {

    this.doctorService.addDoctor(this.doctor).subscribe({

      next: (response) => {

        console.log(
          'Doctor registered successfully:',
          response
        );

        alert('Doctor registered successfully!');

        this.router.navigate(['/login']);

      },

      error: (error) => {

        console.error(
          'Doctor registration failed:',
          error
        );

        alert(
          error.error?.message ||
          'Doctor registration failed!'
        );

      }

    });

  }

}