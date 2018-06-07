import { ApiService } from './api.service';
import { Component } from '@angular/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  _news = [];

  constructor( private api: ApiService) {
    this.getNews();
  }

  getNews() {
    this.api.getNews()
    .subscribe(
      res => {
        this._news = res;
      },
      err => console.log(err)
    );
  }
}
