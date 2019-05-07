import {Component, OnInit} from '@angular/core';
import {HttpClient, HttpHeaders, HttpParams} from '@angular/common/http';
import {LoginDTO} from '../_models/login.response.DTO';
import {Router} from '@angular/router';
import {DataService} from '../data.service';
@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  email: any;
  password: any;
  username: any;

  readonly LOGIN_URL = 'http://localhost:8080/auth/login';
  readonly REGISTER_URL = 'http://localhost:8080/auth/register';

  constructor(private http: HttpClient, private router: Router, private dataService: DataService) {
  }

  ngOnInit() {
  }

  onLoginClick() {
    const headers = new HttpHeaders()
      .set('Content-Type', 'application/json');

    this.http.post<LoginDTO>(this.LOGIN_URL, {
      'email': this.email,
      'password': this.password
    }, {headers: headers}).subscribe((loginDTO: LoginDTO) => {
        if (loginDTO) {
          this.dataService.changeCurrentUser(loginDTO.user);
          console.log(JSON.stringify(loginDTO.user));
          localStorage.setItem('X-AUTH-TOKEN', loginDTO.token);
          this.router.navigate(['/home']);
        } else {
          console.log(loginDTO);
          alert('Bad Credentials!');
        }
    });
  }

  onRegisterClick() {
    const headers = new HttpHeaders()
      .set('Content-Type', 'application/json');

    this.http.post(this.REGISTER_URL, {
      'email': this.email,
      'password': this.password,
      'username': this.username,
    }, {headers: headers}).subscribe((user: any) => {
      console.log(JSON.stringify(user));
      if (user) {
        alert('Succesfully registered! :)');
      } else {
        alert('Could not register');
      }
    });
  }
}
