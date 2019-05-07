import { Component, OnInit } from '@angular/core';
import {Subscription} from 'rxjs';
import {UserDTO} from '../_models/user.response.DTO';
import {HttpClient, HttpParams} from '@angular/common/http';
import {Router} from '@angular/router';
import {DataService} from '../data.service';
import {PostDTO} from '../_models/post.response.DTO';

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.css']
})
export class UserComponent implements OnInit {
  subscription: Subscription;
  loggedUser: UserDTO;
  visitedUser: UserDTO;
  usersPosts: PostDTO[];
  sharedPosts: PostDTO[];

  readonly PHOTOS_URL = 'http://127.0.0.1:8887/';
  readonly FOLLOW_USER = 'http://localhost:8080/user/follow';
  readonly UNFOLLOW_USER = 'http://localhost:8080/user/unfollow';
  readonly GET_USER_URL = 'http://localhost:8080/user/findById';

  constructor(private http: HttpClient, private router: Router, private dataService: DataService) { }

  ngOnInit() {
    this.subscription = this.dataService.currentUser.subscribe(user => {
      console.log('SUBSCRIPTION RECEIVED LOGGED USER ' + user);
      this.loggedUser = user;
    });

    this.subscription.add(this.dataService.visitedUser.subscribe(user => {
      console.log('SUBSCRIPTION RECEIVED VISITED USER ' + user);
      this.visitedUser = user;
      this.getUserByID();
    }));
  }

  getUserByID() {
    const params = new HttpParams()
      .set('id', String(this.visitedUser.id));
    this.http.get<UserDTO>(this.GET_USER_URL, {params: params}).subscribe( (user: any) => {
      if (user) {
        this.visitedUser = user;
        this.usersPosts = this.visitedUser.posts;
        this.sharedPosts = this.visitedUser.sharedPosts;
        console.log(JSON.stringify(this.visitedUser));
      }
    });
  }

  getFullPhotoUrl() {
    if (this.visitedUser.profileImageUrl === undefined) {
      this.visitedUser.profileImageUrl = 'undefined.png';
    }
    const s = this.PHOTOS_URL + this.visitedUser.profileImageUrl;
    return s;
  }

  getPhotoUrl(post: PostDTO) {
    const s =  this.PHOTOS_URL + post.imageUrl;
    console.log(s);
    return s;
  }

  followUser() {
    const params = new HttpParams()
      .set('userId', String(this.visitedUser.id));
    this.http.post(this.FOLLOW_USER, {}, {params: params}).subscribe( (page: any) => {
      if (page) {
        alert('User followed successfully!');
      } else {
        alert('Oops something went wrong...');
      }
    });
  }

  unfollowUser() {
    const params = new HttpParams()
      .set('userId', String(this.visitedUser.id));
    this.http.post(this.UNFOLLOW_USER, {}, {params: params}).subscribe( (page: any) => {
      if (page) {
        alert('User unfollowed successfully!');
      } else {
        alert('Oops something went wrong...');
      }
    });
  }
}
