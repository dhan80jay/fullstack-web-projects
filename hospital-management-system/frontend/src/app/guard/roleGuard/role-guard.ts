import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { AuthService } from '../../service/authService/auth-service';

export const roleGuard: CanActivateFn = (route, state) => {
  const router = inject(Router);
  const authService = inject(AuthService);

  const requiredRole = route.data['role'];
  const userRole = authService.getRole();

  console.log('URL:', state.url);
  console.log('Required Role:', requiredRole);
  console.log('User Role:', userRole);


  if (userRole === requiredRole) {
    return true;
  }

  return router.parseUrl('/access-denied');
};
