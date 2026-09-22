import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { RouterLink } from '@angular/router';
import { DoctorService } from '../../service/doctorService/doctor-service';
import { NgxPaginationModule } from 'ngx-pagination';
import { FormsModule } from '@angular/forms';
import { DoctorEntity } from '../../model/doctor/doctor';

@Component({
  selector: 'app-doctor',
  imports: [RouterLink, NgxPaginationModule, FormsModule],
  templateUrl: './doctor.html',
  styleUrl: './doctor.css',
})
export class Doctor implements OnInit {
  p: number = 1;
  doctors: DoctorEntity[] = [];

  sortedDoctors: DoctorEntity[] = [];

  searchText: string = '';

  ascending = true;

  constructor(
    private doctorService: DoctorService,
    private cdr: ChangeDetectorRef,
  ) {}

  searchDoctorByName() {
    if (this.searchText.trim() === '') {
      this.sortedDoctors = this.doctors;
    } else {
      this.sortedDoctors = this.doctors.filter(
        (doctor) =>
          doctor.firstName.toLowerCase().includes(this.searchText.toLowerCase()) ||
          doctor.lastName.toLowerCase().includes(this.searchText.toLowerCase()),
      );
    }
  }

  filterDoctorsByNames() {
    this.sortedDoctors.sort((a, b) => {
      const result = a.firstName.localeCompare(b.firstName);

      return this.ascending ? result : -result;
    });
    this.ascending = !this.ascending;
  }

  deleteDoctor(id: any): void {
    this.doctorService.deleteDoctor(id).subscribe({
      next: () =>{
        alert('Doctor Deleted Succussfully !');
      },

      error: (error) =>{
        if(error === 409){
          alert(error.error.detail);
        }
        else{
          alert('Unable to delete doctor');
        }
      }
    })
          this.cdr.detectChanges();

  }

  ngOnInit(): void {
    this.doctorService.getAllDoctors().subscribe((response) => {
      this.doctors = response;
      this.sortedDoctors = response;

      this.cdr.detectChanges();
    });
  }
}
