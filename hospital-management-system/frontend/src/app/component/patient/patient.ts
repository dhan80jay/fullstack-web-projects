import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { NgxPaginationModule } from 'ngx-pagination';
import { RouterLink, RouterOutlet } from "@angular/router";
import { PatientService } from '../../service/patient/patient-service';
import { FormsModule } from "@angular/forms";
import { PatientEntity } from '../../model/patient/patient-entity';
@Component({
  selector: 'app-patient',
  imports: [NgxPaginationModule, RouterLink, FormsModule],
  templateUrl: './patient.html',
  styleUrl: './patient.css',
})
export class Patient implements OnInit {
  p: number = 1;
  patients: PatientEntity[] = [];

  sortedPatients:PatientEntity[] = [];

  malePatients: number = 0;
  femalePatients: number = 0;

  today = new Date();

  registeredToday = 0;

  searchText:string='';

  ascending = true;


  constructor(
    private patientService: PatientService,
    private cdr: ChangeDetectorRef,
  ) {}

  calculateRegisteredToday() {
    this.registeredToday = this.patients.filter((patient) => {
      if (!patient.registeredAt) {
        return false;
      }

      const date = new Date(patient.registeredAt);

      return (
        date.getFullYear() === this.today.getFullYear() &&
        date.getMonth() === this.today.getMonth() &&
        date.getDate() === this.today.getDate()
      );
    }).length;
  }

  calculateGenderCount() {
    this.malePatients = this.patients.filter((patient) => patient.gender === 'Male').length;
    this.femalePatients = this.patients.filter((patient) => patient.gender === 'Female').length;
  }


  searchPatientByName(){
 
    if(this.searchText.trim() === ''){
      this.sortedPatients = this.patients;
    }
    else{
   this.sortedPatients = this.patients.filter((patient) => patient.firstName.toLowerCase().includes(this.searchText.toLowerCase())
    || patient.lastName.toLowerCase().includes(this.searchText.toLowerCase()));
    console.log(this.sortedPatients);
    }

  }

 
  filterPatientsByNames(){
     this.sortedPatients.sort((a, b) => {

    const result = a.firstName.localeCompare(b.firstName);

    return this.ascending ? result : -result;

  });

  this.ascending = !this.ascending;

  }

  deletePatient(id:any){
    this.patientService.deletePatient(id).subscribe((res) =>{
       alert('Deleted Successfully !');
       this.ngOnInit();
    })
  }


  ngOnInit(): void {
    this.patientService.getAllPatients().subscribe((response) => {
      this.patients = response;
      this.sortedPatients = response;
      this.calculateGenderCount();

      this.calculateRegisteredToday();

       this.cdr.detectChanges();
    });
  }
}
