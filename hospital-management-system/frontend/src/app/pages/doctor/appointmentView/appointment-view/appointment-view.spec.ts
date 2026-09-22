import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AppointmentView } from './appointment-view';

describe('AppointmentView', () => {
  let component: AppointmentView;
  let fixture: ComponentFixture<AppointmentView>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AppointmentView]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AppointmentView);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
