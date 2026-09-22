import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { ActivatedRoute, RouterLink } from '@angular/router';
 import { DoctorService } from '../../../../service/doctorService/doctor-service';
import { FormsModule } from '@angular/forms';
import { DoctorEntity } from '../../../../model/doctor/doctor';

@Component({
  selector: 'app-update-doctor',
  imports: [RouterLink,FormsModule],
  templateUrl: './update-doctor.html',
  styleUrl: './update-doctor.css',
})
export class UpdateDoctor implements OnInit{
  constructor(private doctorService: DoctorService,
          private cdr:ChangeDetectorRef,
          private activatedRoute:ActivatedRoute
    
  ) {}

  doctorId:any;

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

    appointments: []
  };

  ngOnInit(): void {

    this.activatedRoute.paramMap.subscribe((res) =>{
      this.doctorId = res.get('id');

      this.doctorService.getDoctorById(this.doctorId).subscribe((res) =>{
        this.doctor = res;
        console.log(this.doctor);
        
              this.cdr.detectChanges();

      })
            this.cdr.detectChanges();

    })
    
  }

    updateDoctor() {
    this.doctorService.updateDoctor(this.doctor,this.doctorId).subscribe((response) => {
      this.doctor = response;
      console.log(this.doctor);
      
      this.cdr.detectChanges();
    });
  }


}
