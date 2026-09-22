import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { ActivatedRoute, RouterLink } from "@angular/router";
import { PrescriptionService } from '../../../service/prescriptionSerice/prescription-service';
import { AppointmentService } from '../../../service/appointment/appointment-service';
import { PrescriptionEntity } from '../../../model/prescription/prescription';
import { AppointmentEntity } from '../../../model/appointment/appointment';
import { DatePipe } from '@angular/common';
import { MedicineService } from '../../../service/medicineService/medicine-service';
import { MedicineEntity } from '../../../model/medicine/medicine';

@Component({
  selector: 'app-view-prescription',
  imports: [RouterLink,DatePipe],
  templateUrl: './view-prescription.html',
  styleUrl: './view-prescription.css',
})
export class ViewPrescription implements OnInit{
  prescriptionId:any;

  prescription!:PrescriptionEntity;

  appointment!:AppointmentEntity;

  medicines!:MedicineEntity[];

  constructor(private prescriptionService:PrescriptionService,
              private cdr:ChangeDetectorRef,
              private appointmentService:AppointmentService,
              private activatedRoute:ActivatedRoute,
  ) {
    
  }



  loadAppointmentAndPrescription(){
    this.activatedRoute.paramMap.subscribe((res) =>{
      this.prescriptionId = res.get('prescriptionId');
      console.log(this.prescriptionId);
      
      this.appointmentService.getAppointmentByPrescriptionId(this.prescriptionId).subscribe((res) =>{
        this.appointment = res;
        this.cdr.detectChanges();
        console.log(this.appointment);
        
      })

      this.prescriptionService.getPrescriptionById(this.prescriptionId).subscribe((res) =>{
        this.prescription = res;
        this.cdr.detectChanges();
      })
    })
  }

  printPrescription(): void {
  window.print();
}
  loadMedicines(){
    this.activatedRoute.paramMap.subscribe((res) =>{
      this.prescriptionId = res.get('prescriptionId');
      this.prescriptionService.getMedicinesByPrescriptionId(this.prescriptionId).subscribe((res) =>{
        this.medicines = res;
        this.cdr.detectChanges();
        
      })
    })
  }

  ngOnInit(): void {
    this.loadAppointmentAndPrescription()
    this.loadMedicines();
  }



}
