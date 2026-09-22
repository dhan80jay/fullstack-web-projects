import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { ActivatedRoute, RouterLink } from "@angular/router";
import { AppointmentService } from '../../../../service/appointment/appointment-service';
import { UpdateAppointmentDto } from '../../../../model/appointment/update-appointment.dto';
import { AppointmentStatus } from '../../../../enum/appointment-status';
import { FormsModule } from '@angular/forms';
import { AppointmentEntity } from '../../../../model/appointment/appointment';
import { Location } from '@angular/common';
 @Component({
  selector: 'app-update-appointment',
  imports: [FormsModule],
  templateUrl: './update-appointment.html',
  styleUrl: './update-appointment.css',
})
export class UpdateAppointment implements OnInit{

  appointmentId:any;

  appointment:UpdateAppointmentDto = {
    appointmentDate: '',
    appointmentStatus: AppointmentStatus.BOOKED,
    appointmentTime: '',
    reason: '',
  }
appointmentStatuses = Object.values(AppointmentStatus);
 
constructor(private appointmentService:AppointmentService,
  private activatedRoute:ActivatedRoute,
private cdr:ChangeDetectorRef,
private location:Location){}
 
  ngOnInit(): void {
    
    this.activatedRoute.paramMap.subscribe((res) =>{
        this.appointmentId = res.get('appointmentId');
        console.log(this.appointmentId);
        
        this.appointmentService.getAppointmentById(this.appointmentId).subscribe((res) =>{
          this.appointment = res;
              this.cdr.detectChanges();

        })

    })
  }

 
  updateAppointment(){
    this.appointmentService.updateAppointment(this.appointment,this.appointmentId).subscribe({
      
      next:response =>{
        alert('Appointment updated Successfully !')
      },


      error: error =>{
        if(error.status === 400){
          alert('Appointment cannot update !');
        }
      }
    }
    )
  }

    goBack(): void {
    this.location.back();
  }
}
