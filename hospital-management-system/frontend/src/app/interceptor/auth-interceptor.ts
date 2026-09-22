import { HttpInterceptorFn } from '@angular/common/http';
import { catchError, throwError } from 'rxjs';
import { AuthService } from '../service/authService/auth-service';
import { inject } from '@angular/core';

export const authInterceptor: HttpInterceptorFn = (req, next) => {

  const authService = inject(AuthService);

  const token = authService.getToken();

  if (token) {

    const authRequest = req.clone({
      setHeaders: {
        Authorization: `Bearer ${token}`,
      },
    });

    return next(authRequest).pipe(
      catchError((error) => {
  
        if (error.status === 401) {
          authService.logout();
        }

        if (error.status === 403) {
          console.log('Access denied');
        }

        return throwError(() => error);
      }),
    );
  }

  return next(req);
};