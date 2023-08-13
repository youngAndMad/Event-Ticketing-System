import {inject, Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private BASE_URL:string = 'http://localhost:8090/api/v1/user/'

  private http = inject(HttpClient)


}
