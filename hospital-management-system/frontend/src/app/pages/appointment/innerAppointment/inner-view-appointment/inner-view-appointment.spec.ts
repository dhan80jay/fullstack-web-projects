import { ComponentFixture, TestBed } from '@angular/core/testing';

import { InnerViewAppointment } from './inner-view-appointment';

describe('InnerViewAppointment', () => {
  let component: InnerViewAppointment;
  let fixture: ComponentFixture<InnerViewAppointment>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [InnerViewAppointment]
    })
    .compileComponents();

    fixture = TestBed.createComponent(InnerViewAppointment);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
