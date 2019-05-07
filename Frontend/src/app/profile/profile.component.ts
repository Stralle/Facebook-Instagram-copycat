import { Component, OnInit } from '@angular/core';
import {HttpClient, HttpHeaders, HttpParams} from '@angular/common/http';
import {Router} from '@angular/router';
import {DataService} from '../data.service';
import {Subscription} from 'rxjs';
import {UserDTO} from '../_models/user.response.DTO';
import {PostDTO} from '../_models/post.response.DTO';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {
  typedPassword: any;
  typedEmail: any;
  subscription: Subscription;
  loggedUser: UserDTO;
  image: any;
  imageBase64: any;

  readonly UPDATE_URL = 'http://localhost:8080/user/update';
  readonly PHOTOS_URL = 'http://127.0.0.1:8887/';

  constructor(private http: HttpClient, private router: Router, private dataService: DataService) { }

  ngOnInit() {
    this.subscription = this.dataService.currentUser.subscribe(user => {
      console.log('SUBSCRIPTION RECEIVED USER ' + user);
      this.loggedUser = user;
    });
    this.typedEmail = this.loggedUser.email;
  }

  save() {
    const headers = new HttpHeaders()
      .set('Content-Type', 'application/json');

    this.http.put(this.UPDATE_URL, {
      'email': this.typedEmail,
      'password': this.typedPassword,
      'username': this.loggedUser.username,
      'id': this.loggedUser.id,
      'imageBase64' : this.imageBase64.slice('data:image/png;base64,'.length, this.imageBase64.length)
    }, {headers: headers}).subscribe((user: any) => {
      if (user) {
        alert('Succesfully changed your email and password! :)');
        this.loggedUser = user;
      } else {
        alert('Could not change your credentials');
      }
    });
  }

  getFullPhotoUrl() {
    console.log(this.loggedUser.profileImageUrl + ' before ');
    if (this.loggedUser.profileImageUrl === undefined) {
      this.loggedUser.profileImageUrl = 'undefined.png';
    }
    const s = this.PHOTOS_URL + this.loggedUser.profileImageUrl;
    console.log(s);
    return s;

  }

  onFileChanged(event) {
    this.image = event.target.files[0];

    const reader = new FileReader();
    reader.onload = e => this.imageBase64 = reader.result;

    reader.readAsDataURL(this.image);
  }
}
