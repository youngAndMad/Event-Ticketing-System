import { Component, OnInit, inject } from '@angular/core';
import { EventService } from 'src/app/service/event.service';
import { TicketService } from 'src/app/service/ticket.service';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.scss'],
})
export class ProfileComponent implements OnInit {
  private eventService = inject(TicketService);

  ngOnInit() {
    this.eventService.findById(2).subscribe(console.log);
  }
}
