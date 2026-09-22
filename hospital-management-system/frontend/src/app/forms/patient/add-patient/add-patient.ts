import { Component } from '@angular/core';
import { RouterLink } from "@angular/router";
import { FormsModule } from "@angular/forms";
import { PatientService } from '../../../service/patient/patient-service';
import { PatientEntity } from '../../../model/patient/patient-entity';
import { User } from '../../../model/user/user';
 @Component({
  selector: 'app-add-patient',
  imports: [RouterLink, FormsModule],
  templateUrl: './add-patient.html',
  styleUrl: './add-patient.css',
})
export class AddPatient {

  

  constructor(private patientService:PatientService) {
  }

  patient:PatientEntity = {
      firstName:'',
      lastName:'',
      dateOfBirth:'',
      bloodGroup:'',
      email:'',
      gender:'',
      phoneNumber:'',
      address:'',
      user: {
        username:'',
        password:''
      },
      appointments:[]
    }


  addPatient(){
     this.patientService.addPatient(this.patient).subscribe((response) =>{
      console.log(response);
     });
  }

}
