import { Injectable } from '@angular/core';
import {BehaviorSubject} from 'rxjs';
import {UserDTO} from './_models/user.response.DTO';

@Injectable({
  providedIn: 'root'
})
export class DataService {
  private currentUserSource = new BehaviorSubject<UserDTO>(null);
  private visitedUserSource = new BehaviorSubject<UserDTO>(null);
  public currentUser = this.currentUserSource.asObservable();
  public visitedUser = this.visitedUserSource.asObservable();
  constructor() { }

  changeCurrentUser(userDTO) {
    this.currentUserSource.next(userDTO);
  }

  changeVisitedUser(userDTO) {
    this.visitedUserSource.next(userDTO);
  }
}
