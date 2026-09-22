import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { RouterLink } from '@angular/router';
import { AppointmentStatus } from '../../../enum/appointment-status';
import { PaymentStatus } from '../../../enum/payment-status';
import { AppointmentEntity } from '../../../model/appointment/appointment';
import { BillEntity } from '../../../model/bill/bill';
import { DoctorEntity } from '../../../model/doctor/doctor';
import { PatientEntity } from '../../../model/patient/patient-entity';
import { AppointmentService } from '../../../service/appointment/appointment-service';
import { BillService } from '../../../service/billService/bill-service';
import { FormsModule } from '@angular/forms';
import { DatePipe } from '@angular/common';
import { Bill } from '../../../component/bill/bill';

@Component({
  selector: 'app-add-billing',
  imports: [RouterLink,FormsModule,DatePipe],
  templateUrl: './add-billing.html',
  styleUrl: './add-billing.css',
})
export class AddBilling implements OnInit{
  
  bill: BillEntity = {
    consultationFee: 0,
    medicineCharges: 0,
    otherCharges: 0,
    paymentStatus: PaymentStatus.PENDING,
    billingDate:'',
    totalAmount: 0,
    appointment: {
      appointmentDate: '',
      appointmentTime: '',
      appointmentStatus: AppointmentStatus.BOOKED,
      reason: '',
      doctor: {} as DoctorEntity,
      patient: {} as PatientEntity,
    },
  };

  appointments!:AppointmentEntity[];

  constructor(
    private appointmentService: AppointmentService,
    private billService: BillService,
    private cdr:ChangeDetectorRef
  ) {}

  loadAppointments(){
    this.appointmentService.getAllAppointments().subscribe((res) =>{
      this.appointments = res;
      this.cdr.detectChanges();
    })
  }

  totalAmount(){
  const totalAmountBill =  this.bill.consultationFee + this.bill.medicineCharges + this.bill.otherCharges
  return totalAmountBill;
  }

  createBill(){    
    this.billService.createBill(this.bill).subscribe({
      
      next:(response) => {
        console.log(response);
        
        alert('Bill created !');
      },

      error:(error) =>{
        if(error.status === 409){
        alert('Bill cannot be created !');
        }
      }
    })
  }

  ngOnInit(): void {
    this.loadAppointments();
  }

}
