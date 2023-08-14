import { Event } from './../domain/Event';
import { HttpClient } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { BASE_API } from '../config/config';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class EventService {
  private EVENT_URL: string = `${BASE_API}event/`;
  private http = inject(HttpClient);

  findById(id: number): Observable<any> {
    return this.http.get(`${this.EVENT_URL}${id}`);
  }

  deleteById(id: number): Observable<any> {
    return this.http.delete(`${this.EVENT_URL}${id}`);
  }

  search(props: any): Observable<any> {
    return this.http.put(this.EVENT_URL, { props });
  }

  add(event: any): Observable<any> {
    return this.http.post(this.EVENT_URL, { event });
  }
}
