import { CommonModule, DatePipe, CurrencyPipe } from '@angular/common';
import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { RouterLink, ActivatedRoute, Router } from '@angular/router';
import { AppointmentEntity } from '../../../model/appointment/appointment';
import { DoctorService } from '../../../service/doctorService/doctor-service';
import { PrescriptionService } from '../../../service/prescriptionSerice/prescription-service';
import { MedicineEntity } from '../../../model/medicine/medicine';

@Component({
  selector: 'app-view-doctor-appointment',
  imports: [CommonModule, CurrencyPipe, RouterLink],
  templateUrl: './view-doctor-appointment.html',
  styleUrl: './view-doctor-appointment.css',
})
export class ViewDoctorAppointment implements OnInit {
  appointmentId!: number;

  appointment: AppointmentEntity | null = null;

  medicines: MedicineEntity[] = [];

  loading = true;

  constructor(
    private doctorService: DoctorService,
    private activatedRoute: ActivatedRoute,
    private cdr: ChangeDetectorRef,
    private router: Router,
    private prescriptionService: PrescriptionService,
  ) {}

  ngOnInit(): void {
    this.activatedRoute.paramMap.subscribe((params) => {
      const id = params.get('appointmentId');

      if (id) {
        this.appointmentId = Number(id);
        this.loadAppointment();
      }
    });
  }

  loadAppointment(): void {
    this.loading = true;

    this.doctorService.getLoggedInDoctor().subscribe({
      next: (doctor) => {
        if (!doctor.id) {
          console.error('Doctor ID not found');
          this.loading = false;
          return;
        }

        this.doctorService.getAppointmentByDoctorId(doctor.id).subscribe({
          next: (appointments) => {
            const foundAppointment = appointments.find(
              (appointment) => appointment.id === this.appointmentId,
            );

            if (foundAppointment) {
              this.appointment = foundAppointment;

              console.log('Appointment:', this.appointment);
              console.log('Appointment ID:', this.appointmentId);

              this.prescriptionService
                .getPrescriptionByAppointmentId(this.appointmentId)
                .subscribe({
                  next: (prescription) => {
                    console.log('Prescription:', prescription);

                    if (this.appointment) {
                      this.appointment.prescription = prescription;
                    }

                    const prescriptionId = prescription.id;

                    console.log('Prescription ID:', prescriptionId);

                    if (prescriptionId) {
                      this.prescriptionService
                        .getMedicinesByPrescriptionId(prescriptionId)
                        .subscribe({
                          next: (medicines) => {
                            console.log('Medicines:', medicines);

                            if (this.appointment?.prescription) {
                              this.appointment.prescription.medicine = medicines;
                            }

                            this.cdr.detectChanges();
                          },

                          error: (error) => {
                            console.error('Error loading medicines:', error);
                          },
                        });
                    }

                    this.cdr.detectChanges();
                  },

                  error: (error) => {
                    console.log('No prescription for this appointment');
                  },
                });
            } else {
              console.error('Appointment not found for this doctor');
            }

            this.loading = false;
            this.cdr.detectChanges();
          },

          error: (error) => {
            console.error('Error loading doctor appointments:', error);

            this.loading = false;
          },
        });
      },

      error: (error) => {
        console.error('Error loading logged-in doctor:', error);

        this.loading = false;
      },
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

  getStatusClass(status?: string): string {
    switch (status) {
      case 'BOOKED':
        return 'confirmed';

      case 'PENDING':
        return 'pending';

      case 'COMPLETED':
        return 'completed';

      case 'CANCELLED':
        return 'cancelled';

      default:
        return '';
    }
  }
}
