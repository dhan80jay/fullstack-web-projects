import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ForGotPassword } from './for-got-password';

describe('ForGotPassword', () => {
  let component: ForGotPassword;
  let fixture: ComponentFixture<ForGotPassword>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ForGotPassword]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ForGotPassword);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
