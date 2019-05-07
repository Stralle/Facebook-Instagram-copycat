import { Component, OnInit } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Router} from '@angular/router';
import {DataService} from '../data.service';
import {Subscription} from 'rxjs';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {
  loggedUser: any;
  subscription: Subscription;
  constructor(private http: HttpClient, private router: Router, private dataService: DataService) { }

  ngOnInit() {
    this.subscription = this.dataService.currentUser.subscribe(user => {
      console.log('SUBSCRIPTION RECEIVED USER ' + user);
      this.loggedUser = user;
    });
  }

  onLogoutClick () {
    localStorage.setItem('X-AUTH-TOKEN', '');
    this.router.navigate(['/']);
  }
}
