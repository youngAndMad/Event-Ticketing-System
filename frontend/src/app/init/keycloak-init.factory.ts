import {KeycloakService} from "keycloak-angular";

export function initializeKeycloak(
  keycloak: KeycloakService
) {
  return () =>
    keycloak.init({
      config: {
        url: 'http://localhost:8080' + '/auth',
        realm: 'fs',
        clientId: 'my_client',
      },
      initOptions: {
        redirectUri: 'http://localhost:4200',
        checkLoginIframe: false,
      },
      bearerPrefix: 'Bearer ',
      loadUserProfileAtStartUp: true
    });
}
