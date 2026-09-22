import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { PatientService } from '../../../service/patient/patient-service';
import { ActivatedRoute,RouterLink} from '@angular/router';
import { PatientEntity } from '../../../model/patient/patient-entity';
 @Component({
  selector: 'app-update-patient',
  imports: [FormsModule,RouterLink],
  templateUrl: './update-patient.html',
  styleUrl: './update-patient.css',
})
export class UpdatePatient implements OnInit{

    id:any;
    patientObject:any={}
    constructor(private patientService:PatientService,
      private activatedRoute:ActivatedRoute,private cdr:ChangeDetectorRef) {
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
      }
    }
  

  updatePatient() {
    this.patientService.updatePatient(this.patient,this.id).subscribe((response) =>{
      console.log(response);
    })
  }

   ngOnInit(){
    this.activatedRoute.paramMap.subscribe((param)=>{
      this.id = param.get('id');

      this.patientService.getPatientById(this.id).subscribe((res) =>{
        this.patient  = res;
        console.log(this.patient);
        this.cdr.detectChanges();
      })
    })
  }
}
