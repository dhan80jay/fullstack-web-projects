import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { AppointmentService } from '../../../service/appointment/appointment-service';
import { AppointmentEntity } from '../../../model/appointment/appointment';
import { CurrencyPipe, DatePipe } from '@angular/common';

@Component({
  selector: 'app-view-appointment',
  imports: [RouterLink, DatePipe, CurrencyPipe],
  templateUrl: './view-appointment.html',
  styleUrl: './view-appointment.css',
})
export class ViewAppointment implements OnInit {
  appointmentId: any;

  appointment!: AppointmentEntity;

  constructor(
    private appointmentService: AppointmentService,
    private cdr: ChangeDetectorRef,
    private activatedRoute: ActivatedRoute,
  ) {}

  ngOnInit(): void {
    this.activatedRoute.paramMap.subscribe((res) => {
      this.appointmentId = res.get('appointmentId');

      this.appointmentService.getAppointmentById(this.appointmentId).subscribe((res) => {
        this.appointment = res;
        this.cdr.detectChanges();
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
