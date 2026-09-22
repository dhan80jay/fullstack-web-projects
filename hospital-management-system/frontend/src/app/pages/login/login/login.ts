import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { LoginRequest } from '../../../model/login/login-request';
import { LoginService } from '../../../service/login/login-service';
import { Router, RouterLink } from '@angular/router';
@Component({
  selector: 'app-login',
  imports: [FormsModule,RouterLink],
  templateUrl: './login.html',
  styleUrl: './login.css',
})
export class Login {
  loginRequest: LoginRequest = {
    username: '',
    password: '',
  };

  constructor(
    private loginService: LoginService,
    private router: Router,
  ) {}

  loginInfo() {
    localStorage.removeItem('token');
    localStorage.removeItem('role');

    this.loginService.login(this.loginRequest).subscribe((response) => {
      if (response !== null) {
        localStorage.setItem('token', response.token);
        localStorage.setItem('role', response.role);
        if(response.role === 'ROLE_ADMIN'){
        this.router.navigate(['/dashboard/admin']);
        }
        else if(response.role === 'ROLE_PATIENT'){
          this.router.navigate(['/dashboard/patient']);
        }
        else if(response.role === 'ROLE_DOCTOR'){
          this.router.navigate(['/dashboard/doctor']);
        }
        else{
        this.router.navigate(['/access-denied']);
        }
      } else {
        console.log('Login failed');
      }
    });
  }
}
