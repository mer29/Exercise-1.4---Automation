import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Observable';

// tslint:disable-next-line:import-blacklist
import 'rxjs/Rx';

@Injectable()
export class ApiService {

  url = 'http://localhost/api/';

  constructor(private http: Http) { }

  getNews() {
    return this.http
      .get(this.url + 'newses')
      .map((response: Response) => {
        return response.json();
      })
      .catch(this.handleError);
  }

  private handleError(error: Response) {
    console.log('error from the api service ' + error);
    return Observable.throw(error.statusText);
  }
}
