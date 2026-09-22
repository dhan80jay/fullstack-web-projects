import { ChangeDetectorRef, Component } from '@angular/core';
import { RouterLink } from '@angular/router';
import { PrescriptionService } from '../../service/prescriptionSerice/prescription-service';
import { PrescriptionEntity } from '../../model/prescription/prescription';
import { FormsModule } from '@angular/forms';
import { DatePipe } from '@angular/common';
import { AppointmentService } from '../../service/appointment/appointment-service';
import { NgxPaginationModule } from 'ngx-pagination';

@Component({
  selector: 'app-prescription',
  imports: [RouterLink,FormsModule,DatePipe,NgxPaginationModule],
  templateUrl: './prescription.html',
  styleUrl: './prescription.css',
})
export class Prescription {
  p: number = 1;
  prescriptions: PrescriptionEntity[] = [];

  sortedPrescription:PrescriptionEntity[] = [];


  today = new Date();

  registeredToday = 0;

  searchText:string='';

  ascending = true;


  constructor(
    private prescriptionService: PrescriptionService,
    private cdr: ChangeDetectorRef,
    private appointmentService:AppointmentService
  ) {}

  todaysPrescription() {
    this.registeredToday = this.prescriptions.filter((prescription) => {
      if (!prescription.prescriptionDate) {
        return false;
      }

      const date = new Date(prescription.prescriptionDate);

      return (
        date.getFullYear() === this.today.getFullYear() &&
        date.getMonth() === this.today.getMonth() &&
        date.getDate() === this.today.getDate()
      );
    }).length;
  }


  searchPatientByName(){
 
    if(this.searchText.trim() === ''){
      this.sortedPrescription = this.prescriptions;
    }
    else{
   this.sortedPrescription = this.prescriptions.filter((prescription) => prescription.appointment.patient.firstName.toLowerCase().includes(this.searchText.toLowerCase())
    || prescription.appointment.patient.lastName.toLowerCase().includes(this.searchText.toLowerCase()));
      this.cdr.detectChanges();
   }

  }

 
  filterPatientsByNames(){
     this.sortedPrescription.sort((a, b) => {

    const result = a.appointment.patient.firstName.localeCompare(b.appointment.patient.firstName);

    return this.ascending ? result : -result;

  });

  this.ascending = !this.ascending;

  }

  deletePrescription(id:any){
    this.prescriptionService.deletePrescription(id).subscribe((res) =>{
       alert('Deleted Successfully !');
       this.ngOnInit();
       this.cdr.detectChanges();
    })
  }


  

ngOnInit(): void {
  this.prescriptionService.getAllPrescriptions().subscribe({
    next: (prescriptions) => {

      this.appointmentService.getAllAppointments().subscribe({
        next: (appointments) => {

          this.prescriptions = prescriptions.map((prescription) => {

            const appointment = appointments.find(
              appointment =>
                appointment.prescription?.id === prescription.id
            );

            return {
              ...prescription,
              appointment: appointment!
            };
          });

          this.sortedPrescription = [...this.prescriptions];

          this.todaysPrescription();

          console.log('Prescriptions:', this.prescriptions);

          this.cdr.detectChanges();
        },

        error: (err) => {
          console.error('Error loading appointments:', err);
        }
      });

    },

    error: (err) => {
      console.error('Error loading prescriptions:', err);
    }
  });

  }
}
