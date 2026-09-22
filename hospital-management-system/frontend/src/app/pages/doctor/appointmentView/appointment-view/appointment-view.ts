import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { AppointmentEntity } from '../../../../model/appointment/appointment';
import { AppointmentService } from '../../../../service/appointment/appointment-service';
import { DatePipe } from '@angular/common';

@Component({
  selector: 'app-appointment-view',
  imports: [RouterLink,DatePipe],
  templateUrl: './appointment-view.html',
  styleUrl: './appointment-view.css',
})
export class AppointmentView implements OnInit {
  appointment!: AppointmentEntity;
  appointmentId: any;

  doctorId!:any;

  constructor(
    private cdr: ChangeDetectorRef,
    private activatedRoute: ActivatedRoute,
    private appointmentSerice: AppointmentService,
  ) {}

  ngOnInit(): void {
    this.activatedRoute.paramMap.subscribe((res) => {
      this.appointmentId = res.get('appointmentId');
      this.doctorId = res.get('appointmentId');
      
      this.appointmentSerice.getAppointmentById(this.appointmentId).subscribe((res) => {
        this.appointment = res;
        console.log(this.appointment);
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
