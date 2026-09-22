import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UpdateBill } from './update-bill';

describe('UpdateBill', () => {
  let component: UpdateBill;
  let fixture: ComponentFixture<UpdateBill>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [UpdateBill]
    })
    .compileComponents();

    fixture = TestBed.createComponent(UpdateBill);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
