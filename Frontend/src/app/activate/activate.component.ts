import { Component, OnInit } from '@angular/core';
import {HttpClient, HttpHeaders, HttpParams} from '@angular/common/http';
import {Router} from '@angular/router';

@Component({
  selector: 'app-activate',
  templateUrl: './activate.component.html',
  styleUrls: ['./activate.component.css']
})
export class ActivateComponent implements OnInit {
  key: any;

  readonly ACTIVATE_URL = 'http://localhost:8080/auth/activateAccount';

  constructor(private http: HttpClient, private router: Router) { }

  ngOnInit() {
  }

  onActivateClick() {
    const headers = new HttpHeaders()
      .set('Content-Type', 'application/json');
    const params = new HttpParams()
      .set('key', this.key);

    this.http.post(this.ACTIVATE_URL, {}, {headers: headers, params: params}).subscribe((key: any) => {
      console.log(JSON.stringify(key));
      if (key) {
        alert('Succesfully activated account! :)');
        this.router.navigate(['/login']);
      } else {
        alert('Oops wrong key! :/ ');
      }
    });
  }

}
