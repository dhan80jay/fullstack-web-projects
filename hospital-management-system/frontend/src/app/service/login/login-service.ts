import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { LoginRequest } from '../../model/login/login-request';
import { ResetPassword } from '../../model/reset-password';
@Injectable({
  providedIn: 'root',
})
export class LoginService {
  url = 'http://localhost:8080/api/auth';

  constructor(private httpClient: HttpClient) {}

  login(userData: LoginRequest) {
    return this.httpClient.post<{ token: string; role: string }>(`${this.url}/login`, userData);
  }

  resetPassword(userData: ResetPassword){
    return this.httpClient.put(`${this.url}/forgot`,userData,{responseType:'text'});
  }


}
