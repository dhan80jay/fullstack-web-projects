import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EditPrescription } from './edit-prescription';

describe('EditPrescription', () => {
  let component: EditPrescription;
  let fixture: ComponentFixture<EditPrescription>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [EditPrescription]
    })
    .compileComponents();

    fixture = TestBed.createComponent(EditPrescription);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
