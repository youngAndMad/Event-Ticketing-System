import {KeycloakService} from "keycloak-angular";

export function initializeKeycloak(
    keycloak: KeycloakService
) {
    return () =>
        keycloak.init({
            config: {
                url: 'http://localhost:8080' + '/auth',
                realm: 'event_ticket_system',
                clientId: 'my_client',
            },
            initOptions: {

                pkceMethod: 'S256',
                // must match to the configured value in keycloak
                redirectUri: 'http://localhost:4200/your_url',
                // this will solved the error
                checkLoginIframe: false
            }
        });
}
