import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { LoginService } from '../../../service/login/login-service';
import { ResetPassword } from '../../../model/reset-password';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-for-got-password',
  imports: [FormsModule,RouterLink],
  templateUrl: './for-got-password.html',
  styleUrl: './for-got-password.css',
})
export class ForGotPassword {
  confirmPassword = '';

  user: ResetPassword = {
    email: '',
    password: '',
    username: '',
  };
  constructor(private loginService: LoginService) {}

  resetPassword() {
    this.loginService.resetPassword(this.user).subscribe({
      next: (response) => {
        alert('Password reset successfully !');
      },

      error: (error) => {
        console.log('Not updated');
      },
    });
  }
}
