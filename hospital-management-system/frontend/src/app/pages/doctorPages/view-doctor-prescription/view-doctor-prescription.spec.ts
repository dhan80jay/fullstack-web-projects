import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ViewDoctorPrescription } from './view-doctor-prescription';

describe('ViewDoctorPrescription', () => {
  let component: ViewDoctorPrescription;
  let fixture: ComponentFixture<ViewDoctorPrescription>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ViewDoctorPrescription]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ViewDoctorPrescription);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
