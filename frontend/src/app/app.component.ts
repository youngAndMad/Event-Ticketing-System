import {Component, inject, OnInit} from '@angular/core';
import {HttpClient} from "@angular/common/http";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent  implements OnInit {
  http = inject(HttpClient);

  ngOnInit(): void {
    console.log('fe')
    this.http.get('http://localhost:8083/api/v1/ticket/2')
      .subscribe(console.log);
  }
}