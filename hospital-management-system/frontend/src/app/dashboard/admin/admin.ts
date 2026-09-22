import { ChangeDetectorRef, Component, OnInit, signal } from '@angular/core';
import { RouterLink, RouterOutlet } from "@angular/router";
import { PatientService } from '../../service/patient/patient-service';
import { AppointmentService } from '../../service/appointment/appointment-service';
import { DoctorService } from '../../service/doctorService/doctor-service';
import { AuthService } from '../../service/authService/auth-service';
@Component({
  selector: 'app-admin',
  imports: [RouterLink, RouterOutlet],
  templateUrl: './admin.html',
  styleUrl: './admin.css',
})
  export class Admin implements OnInit{
    
  totalPatients = signal(0);
  totalDoctors = signal(0);
  totalAppointments = signal(0);

  constructor(private patientService: PatientService,
    private appointmentService:AppointmentService,
  private doctorService:DoctorService,
  private authService:AuthService
) {}

  ngOnInit() {  

    this.patientService.getAllPatients().subscribe(response => {

      this.totalPatients.set(response.length);
    });

    this.doctorService.getAllDoctors().subscribe((res) =>{
      this.totalDoctors.set(res.length);
    })

    this.appointmentService.getAllAppointments().subscribe((res) =>{
      this.totalAppointments.set(res.length);
    })

  }

  logout(){
    this.authService.logout();
  }

    
}
