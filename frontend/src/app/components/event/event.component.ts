import { Component, OnInit, inject } from '@angular/core';
import { EventService } from 'src/app/service/event.service';

@Component({
  selector: 'app-event',
  templateUrl: './event.component.html',
  styleUrls: ['./event.component.scss'],
})
export class EventComponent implements OnInit {
  private eventService = inject(EventService);

  ngOnInit() {
    this.eventService.findById(7).subscribe(console.log);
  }
}
