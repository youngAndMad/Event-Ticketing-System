import { inject, Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { BASE_API } from '../config/config';
import { Observable } from 'rxjs';
import { User } from '../domain/User';
import { KeycloakService } from 'keycloak-angular';

@Injectable({
  providedIn: 'root',
})
export class UserService {
  private USER_URL: string = `${BASE_API}user/`;
  private http = inject(HttpClient);
  private keycloak = inject(KeycloakService);

  register(req: any): Observable<any> {
    return this.http.post(this.USER_URL, req);
  }

  findById(id: number): Observable<any> {
    return this.http.get(`${this.USER_URL}/${id}`);
  }

  update(updated: User): Observable<any> {
    return this.http.put(`${this.USER_URL}/${updated.id}`, { updated });
  }

  logout() {
    this.keycloak.logout();
  }
}
