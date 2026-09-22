import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ViewDoctorAppointment } from './view-doctor-appointment';

describe('ViewDoctorAppointment', () => {
  let component: ViewDoctorAppointment;
  let fixture: ComponentFixture<ViewDoctorAppointment>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ViewDoctorAppointment]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ViewDoctorAppointment);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
