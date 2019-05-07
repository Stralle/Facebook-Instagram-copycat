import { Component, OnInit } from '@angular/core';
import {UserDTO} from '../_models/user.response.DTO';
import {HttpClient, HttpParams} from '@angular/common/http';
import {DataService} from '../data.service';
import {Router} from '@angular/router';

@Component({
  selector: 'app-search-users',
  templateUrl: './search-users.component.html',
  styleUrls: ['./search-users.component.css']
})
export class SearchUsersComponent implements OnInit {
  availableUsers: UserDTO[];
  typedSearchbox: any;

  readonly GET_ALL_USERS_URL = 'http://localhost:8080/user/findAll';
  readonly FOLLOW_USER = 'http://localhost:8080/user/follow';
  readonly UNFOLLOW_USER = 'http://localhost:8080/user/unfollow';

  constructor(private http: HttpClient, private dataService: DataService, private router: Router) {
    this.getAvailableUsers();
  }

  ngOnInit() {
  }

  getAvailableUsers() {
    this.http.get<UserDTO[]>(this.GET_ALL_USERS_URL).subscribe( (page: any) => {
      this.availableUsers = page.content;
    });
  }

  getFilteredUsers() {
    const params = new HttpParams()
      .set('username', this.typedSearchbox);
    this.http.get<UserDTO[]>(this.GET_ALL_USERS_URL, {params: params}).subscribe( (page: any) => {
      this.availableUsers = page.content;
    });
  }

  search() {
    console.log('Typed in searchbox: ' + this.typedSearchbox);
    if (this.typedSearchbox !== '') {
      this.getFilteredUsers();
    }
  }

  followUser(user: UserDTO) {
    const params = new HttpParams()
      .set('userId', String(user.id));
    this.http.post(this.FOLLOW_USER, {}, {params: params}).subscribe( (page: any) => {
      if (page) {
        alert('User followed successfully!');
      } else {
        alert('Oops something went wrong...');
      }
    });
  }

  unfollowUser(user: UserDTO) {
    const params = new HttpParams()
      .set('userId', String(user.id));
    this.http.post(this.UNFOLLOW_USER, {}, {params: params}).subscribe( (page: any) => {
      if (page) {
        alert('User unfollowed successfully!');
      } else {
        alert('Oops something went wrong...');
      }
    });
  }

  goToUser(user: UserDTO) {
    this.dataService.changeVisitedUser(user);
    this.router.navigate(['/user']);
  }
}
