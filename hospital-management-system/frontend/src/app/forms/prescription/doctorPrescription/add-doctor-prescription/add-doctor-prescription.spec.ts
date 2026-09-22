import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddDoctorPrescription } from './add-doctor-prescription';

describe('AddDoctorPrescription', () => {
  let component: AddDoctorPrescription;
  let fixture: ComponentFixture<AddDoctorPrescription>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AddDoctorPrescription]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AddDoctorPrescription);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
