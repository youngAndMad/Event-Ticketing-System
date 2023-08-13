import { HttpClient } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { BASE_API } from '../config/config';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class TicketService {
  private TICKET_URL: string = `${BASE_API}/ticket/`;
  private http = inject(HttpClient);

  bought(userId: number, eventId: number): Observable<any> {
    return this.http.post(
      this.TICKET_URL,
      {},
      {
        params: {
          userId,
          eventId,
        },
      }
    );
  }

  findById(id: number): Observable<any> {
    return this.http.get(`${this.TICKET_URL}${id}`);
  }

  returnTicket(id: number) {
    return this.http.delete(`${this.TICKET_URL}${id}`);
  }
}
