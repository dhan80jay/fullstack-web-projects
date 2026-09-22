import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ViewDoctorPatient } from './view-doctor-patient';

describe('ViewDoctorPatient', () => {
  let component: ViewDoctorPatient;
  let fixture: ComponentFixture<ViewDoctorPatient>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ViewDoctorPatient]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ViewDoctorPatient);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
