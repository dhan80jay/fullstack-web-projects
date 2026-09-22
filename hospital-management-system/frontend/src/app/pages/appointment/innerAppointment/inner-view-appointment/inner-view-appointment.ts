import { ChangeDetectorRef, Component } from '@angular/core';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { AppointmentEntity } from '../../../../model/appointment/appointment';
import { AppointmentService } from '../../../../service/appointment/appointment-service';
import { PatientService } from '../../../../service/patient/patient-service';
import { DatePipe } from '@angular/common';
import { PatientEntity } from '../../../../model/patient/patient-entity';

@Component({
  selector: 'app-inner-view-appointment',
  imports: [DatePipe, RouterLink],
  templateUrl: './inner-view-appointment.html',
  styleUrl: './inner-view-appointment.css',
})
export class InnerViewAppointment {
  patient!: PatientEntity;
  patientId: any;
  appointmentId: any;
  appointment?: AppointmentEntity;

  constructor(
    private patientService: PatientService,
    private activatedRoute: ActivatedRoute,
    private appointmentService: AppointmentService,
    private cdr: ChangeDetectorRef,
  ) {}

  ngOnInit() {
    this.activatedRoute.paramMap.subscribe((res) => {
      this.appointmentId = res.get('appointmentId');
      this.patientId = res.get('patientId');

 
      this.appointmentService.getAppointmentById(this.appointmentId).subscribe((response) => {
        this.appointment = response;
         this.cdr.detectChanges();
      });
      this.patientService.getPatientById(this.patientId).subscribe((response) => {
        this.patient = response;
      });
    });
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
}
