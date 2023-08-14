import { UserService } from './../service/user.service';
import { Injectable, inject } from '@angular/core';
import {
  ActivatedRouteSnapshot,
  RouterStateSnapshot,
  UrlTree,
  Router,
} from '@angular/router';
import { KeycloakAuthGuard, KeycloakService } from 'keycloak-angular';

@Injectable({
  providedIn: 'root',
})
export class AuthGuard extends KeycloakAuthGuard {
  constructor(
    protected override readonly router: Router,
    protected readonly keycloak: KeycloakService
  ) {
    super(router, keycloak);
  }

  private userService = inject(UserService);

  async isAccessAllowed(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ): Promise<boolean | UrlTree> {
    if (!this.authenticated) {
      await this.keycloak.login({
        redirectUri: window.location.origin + state.url,
      });
    }
    if (!localStorage.getItem('user') && this.authenticated) {
      const keycloakUser = this.keycloak.getKeycloakInstance().idTokenParsed!;
      let user = {
        email: keycloakUser['email'],
        firstName: keycloakUser['given_name'],
        lastName: keycloakUser['family_name'],
      };
      console.log('sending request to registration');
      this.userService.register(user).subscribe((res) => {
        console.log('response from registration', res);
        localStorage.setItem('user', JSON.stringify(res));
      });
    }

    return this.authenticated;
  }
}
