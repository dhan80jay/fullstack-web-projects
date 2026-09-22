import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { ActivatedRoute, RouterLink, RouterOutlet } from '@angular/router';
import { PatientService } from '../../../service/patient/patient-service';
import { DatePipe } from '@angular/common';
import { AppointmentService } from '../../../service/appointment/appointment-service';
import { AppointmentEntity } from '../../../model/appointment/appointment';
import { NgxPaginationModule } from 'ngx-pagination';
import { PatientEntity } from '../../../model/patient/patient-entity';

@Component({
  selector: 'app-view-patient',
  imports: [RouterLink, DatePipe,NgxPaginationModule],
  templateUrl: './view-patient.html',
  styleUrl: './view-patient.css',
})
export class ViewPatient implements OnInit {
  appointmentPage = 1;
billingPage = 1;
  patient!: PatientEntity;
  id: any;

  appointments:AppointmentEntity[]=[];

  constructor(
    private patientService: PatientService,
    private activatedRoute: ActivatedRoute,
    private appointmentService:AppointmentService,
    private cdr:ChangeDetectorRef
  ) {}

  ngOnInit() {
    this.activatedRoute.paramMap.subscribe((res) => {
      this.id = res.get('patientId');

      this.patientService.getPatientById(this.id).subscribe((response) => {
        this.patient = response;
        this.cdr.detectChanges();
      });

      this.appointmentService.getAppointmentsByPatientId(this.id).subscribe((response) =>{
        this.appointments = response;
            this.cdr.detectChanges();

      });
    });

  }

  formatTime(time: string): string {
  const [hours, minutes] = time.split(':');

  const hour = Number(hours);
  const ampm = hour >= 12 ? 'PM' : 'AM';
  const displayHour = hour % 12 || 12;

  return `${displayHour}:${minutes} ${ampm}`;
}
}
