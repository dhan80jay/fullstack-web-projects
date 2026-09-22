import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { ActivatedRoute, RouterLink, RouterOutlet } from "@angular/router";
import { DoctorService } from '../../../service/doctorService/doctor-service';
import { AppointmentService } from '../../../service/appointment/appointment-service';
import { DatePipe } from '@angular/common';
import { AppointmentEntity } from '../../../model/appointment/appointment';
import { AppointmentStatus } from '../../../enum/appointment-status';
import { NgxPaginationModule } from 'ngx-pagination';
import { DoctorEntity } from '../../../model/doctor/doctor';

@Component({
  selector: 'app-view-doctor',
  imports: [RouterLink, DatePipe,NgxPaginationModule],
  templateUrl: './view-doctor.html',
  styleUrl: './view-doctor.css',
})
export class ViewDoctor implements OnInit{
  p: number = 1;

  doctorId:any;
  appointmentId:any;




  doctor:DoctorEntity = {
    firstName:'',
    lastName:'',
    email:'',
    experienceYears:0,
    phoneNumber:'',
    qualification:'',
    specialization:'',
    user:{
      password:'',
      username:''
    },
    appointments:[]
  }

  appointment!:AppointmentEntity

  constructor(private doctorService:DoctorService,
    private cdr:ChangeDetectorRef,
    private activatedRoute:ActivatedRoute,
    private appointmentSerice:AppointmentService
  ){}



  ngOnInit(): void {
    this.activatedRoute.paramMap.subscribe((res) =>{
      this.doctorId = res.get('doctorId');
      this.appointmentId = res.get('appointmentId');
      this.doctorService.getDoctorById(this.doctorId).subscribe((response) =>{
        this.doctor = response;
        this.cdr.detectChanges();
      });

 
      this.doctorService.getAppointmentByDoctorId(this.doctorId).subscribe((response) =>{
        this.doctor.appointments = response;
        this.cdr.detectChanges();

      })
    })
  }

    formatTime(time: string): string {
  const [hours, minutes] = time.split(':');

  const hour = Number(hours);
  const ampm = hour >= 12 ? 'PM' : 'AM';
  const displayHour = hour % 12 || 12;

  return `${displayHour}:${minutes} ${ampm}`;
}

}
