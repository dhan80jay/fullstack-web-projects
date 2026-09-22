import { inject, Injectable } from '@angular/core';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  
   router = inject(Router);
   

   isLoggedIn(){
    return this.getToken() !== null;
   }

   logout(){
     localStorage.removeItem('token');
     localStorage.removeItem('role');
     this.router.navigate(['/login']);
   }

  getToken(): string | null {
  return localStorage.getItem('token');
  }

  getRole():string | null{
    return localStorage.getItem('role');
  }
}
