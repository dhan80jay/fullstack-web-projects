import { ChangeDetectorRef, Component } from '@angular/core';
import { RouterLink } from "@angular/router";
import { NgxPaginationModule } from 'ngx-pagination';
import { AppointmentEntity } from '../../model/appointment/appointment';
import { AppointmentService } from '../../service/appointment/appointment-service';
import { AppointmentStatus } from '../../enum/appointment-status';
import { DatePipe } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-appointment',
  imports: [RouterLink,NgxPaginationModule,DatePipe,FormsModule],
  templateUrl: './appointment.html',
  styleUrl: './appointment.css',
})
export class Appointment {
  p: number = 1;
  appointments: AppointmentEntity[] = [];

  sortedAppointment:AppointmentEntity[] = [];

  pendingAppointments=0;

  completedAppointments=0;

  today = new Date();

  registeredToday = 0;

  searchText:string='';

  ascending = true;


  constructor(
    private appointmentService: AppointmentService,
    private cdr: ChangeDetectorRef,
  ) {}

calculateTodaysAppointments(): void {
  this.registeredToday = this.appointments.filter((appointment) => {

    if (!appointment.appointmentDate) {
      return false;
    }

    const date = new Date(appointment.appointmentDate);

    return (
      date.getFullYear() === this.today.getFullYear() &&
      date.getMonth() === this.today.getMonth() &&
      date.getDate() === this.today.getDate()
    );

  }).length;
}

filterAppointmentByPatientName() {
  this.sortedAppointment.sort((a, b) => {
    const result = a.patient.firstName.localeCompare(
      b.patient.firstName
    );

    return this.ascending ? result : -result;
  });

  this.ascending = !this.ascending;
}
  searchAppointmentByPatientName(){
 
    if(this.searchText.trim() === ''){
      this.sortedAppointment = this.appointments;
    }
    else{
   this.sortedAppointment = this.appointments.filter((appointment) =>appointment.patient.firstName.toLowerCase().includes(this.searchText.toLowerCase())
    || appointment.patient.lastName.toLowerCase().includes(this.searchText.toLowerCase()));
    this.cdr.detectChanges();
  }

  }

  calculatePendingAppointments(){
   this.pendingAppointments = this.appointments.filter((appointment) =>{
     return appointment.appointmentStatus === AppointmentStatus.PENDING;
    }).length
  }
 
  calculateCompletedAppointments(){
     this.completedAppointments = this.sortedAppointment.filter((appointment) =>{
     return appointment.appointmentStatus === AppointmentStatus.COMPLETED;
     }).length;
  }

  deleteAppointment(id:any){
    this.appointmentService.deleteAppointment(id).subscribe((res) =>{
       alert('Deleted Successfully !');
       console.log(id);
       
       this.ngOnInit();
    })
  }

    formatTime(time?: string): string {
    if (!time) {
      return '';
    }

    const [hours, minutes] = time.split(':');

    const hour = Number(hours);
    const ampm = hour >= 12 ? 'PM' : 'AM';
    const displayHour = hour % 12 || 12;

    return `${displayHour}:${minutes} ${ampm}`;
  }


  ngOnInit(): void {
    this.appointmentService.getAllAppointments().subscribe((response) => {
      this.appointments = response;
      this.sortedAppointment = response;

      this.calculateTodaysAppointments();
      this. calculatePendingAppointments();
      this.calculateCompletedAppointments();
       this.cdr.detectChanges();
    });
  }
}
