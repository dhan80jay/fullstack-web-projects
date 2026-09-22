import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { RouterLink } from "@angular/router";
import { BookAppointmentDto } from '../../../model/appointment/book-appointment-dto';
import { DoctorEntity } from '../../../model/doctor/doctor';
import { AppointmentService } from '../../../service/appointment/appointment-service';
import { PatientService } from '../../../service/patient/patient-service';
import { DoctorService } from '../../../service/doctorService/doctor-service';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-book-appointment',
  imports: [RouterLink,FormsModule],
  templateUrl: './book-appointment.html',
  styleUrl: './book-appointment.css',
})
export class BookAppointment implements OnInit{

  appointment:BookAppointmentDto = {
    appointmentDate:'',
    appointmentTime:'',
    doctor: {} as DoctorEntity,
    reason:''
  }
  patientId?:number;

  doctors:DoctorEntity [] = [];

  constructor(private appointmentService:AppointmentService,
      private patientService:PatientService,
      private doctorService:DoctorService,
      private cdr:ChangeDetectorRef
  ) {
    
  }

  getPatientId(){
    this.patientService.getPatientByUsername().subscribe({
      next:(response) => {
        this.patientId = response.id
      },
      error:(error) =>{
        console.log(error);
      }
    })
  }

  loadDoctors(){
    this.doctorService.getAllDoctors().subscribe({
      next:(response) =>{
        this.doctors = response;
       this.getPatientId();
        this.cdr.detectChanges();
      },

      error:(error) =>{
        console.log(error);
      }
    })
  }

  bookAppointment(){
    this.appointmentService.bookAppointmentByPatient(this.appointment,this.patientId).subscribe({
      next:(response) =>{
        alert('Appointment confirmed !');
        console.log(response);
      },

      error:(error) =>{
        if(error.status === 409){
          alert('Doctor slot not available !');
        }
      }

    })
  }

  ngOnInit(): void {
    this.loadDoctors();
  }
 
}
