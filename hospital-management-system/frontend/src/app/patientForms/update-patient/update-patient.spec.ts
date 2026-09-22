import { ComponentFixture, TestBed } from '@angular/core/testing';

import {UpdatePatientByPatient } from './update-patient';

describe('UpdatePatient', () => {
  let component: UpdatePatientByPatient;
  let fixture: ComponentFixture<UpdatePatientByPatient>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [UpdatePatientByPatient]
    })
    .compileComponents();

    fixture = TestBed.createComponent(UpdatePatientByPatient);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
