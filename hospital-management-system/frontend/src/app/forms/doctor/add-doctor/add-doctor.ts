import { Component } from '@angular/core';

import { ActivatedRoute, RouterLink } from '@angular/router';

import { FormsModule } from '@angular/forms';
import { DoctorService } from '../../../service/doctorService/doctor-service';
import { DoctorEntity } from '../../../model/doctor/doctor';

@Component({
  selector: 'app-add-doctor',
  imports: [RouterLink, FormsModule],
  templateUrl: './add-doctor.html',
  styleUrl: './add-doctor.css',
})
export class AddDoctor {
  constructor(private doctorService: DoctorService,private activatedRoute:ActivatedRoute) {}

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

  addDoctor() {
    this.doctorService.addDoctor(this.doctor).subscribe( {
      next:(next) =>{
        alert('Doctor added successfully !');
      },

      error:(error) =>{
        alert('Cannot added !');
      }
    });
  }
}
