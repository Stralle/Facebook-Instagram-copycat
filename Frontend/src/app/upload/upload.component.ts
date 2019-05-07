import { Component, OnInit } from '@angular/core';
import {HttpClient, HttpHeaders, HttpParams} from '@angular/common/http';
import {LoginDTO} from '../_models/login.response.DTO';
import {Router} from '@angular/router';
import {PostDTO} from '../_models/post.response.DTO';
import {UserDTO} from '../_models/user.response.DTO';
import {DataService} from '../data.service';
import {subscriptionLogsToBeFn} from 'rxjs/internal/testing/TestScheduler';
import {Subscription} from 'rxjs';

@Component({
  selector: 'app-upload',
  templateUrl: './upload.component.html',
  styleUrls: ['./upload.component.css']
})
export class UploadComponent implements OnInit {
  description: any;
  image: any;

  readonly UPLOAD_URL = 'http://localhost:8080/post/save';
  readonly GET_USER_BY_ID = 'http://localhost:8080/user/findById';
  imageBase64: any;
  subscription: Subscription;
  loggedUser: UserDTO;
  constructor(private http: HttpClient, private router: Router, private dataService: DataService) { }

  ngOnInit() {
    this.subscription = this.dataService.currentUser.subscribe(user => {
      console.log('SUBSCRIPTION RECEIVED USER ' + user);
      this.loggedUser = user;
    });
  }

  onFileChanged(event) {
    this.image = event.target.files[0];

    const reader = new FileReader();
    reader.onload = e => this.imageBase64 = reader.result;

    reader.readAsDataURL(this.image);
  }

  onUploadClick() {


    const headers = new HttpHeaders()
      .set('Content-Type', 'application/json');

    const body = {
      'description': this.description,
      'imageBase64' : this.imageBase64.slice('data:image/png;base64,'.length, this.imageBase64.length)
    };

    const params = new HttpParams()
      .set('id', this.loggedUser.id + '');

    this.http.post(this.UPLOAD_URL, body).subscribe((post: PostDTO) => {

      if (post) {

        this.http.get(this.GET_USER_BY_ID, {params: params}).subscribe( (user: UserDTO) => {
          if (user) {
            this.dataService.changeCurrentUser(user);
          }

          this.loggedUser.posts.sort((a, b) => {
            const datA = new Date(a.createdAt).getMilliseconds();
            const datB = new Date(b.createdAt).getMilliseconds();


            return datA - datB;
          });
        });
        alert('Successfully added new photo! :D');
      } else {
        alert('Oops something went wrong!');
      }
    });
  }
}
