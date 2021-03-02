import { Component, OnInit } from '@angular/core';
import { AuthenticationService } from '../authentication.service';
import { User } from '../user';


@Component({
  selector: 'app-main-nav',
  templateUrl: './main-nav.component.html',
  styleUrls: ['./main-nav.component.css']
})
export class MainNavComponent implements OnInit {
  public loggedIn=false;
  constructor(private authService:AuthenticationService) { }

    user:User;
  ngOnInit(): void {
    this.loggedIn = this.authService.isLoggedIn();
  }

  logoutUser(){
    this.authService.logout();
    location.reload();
  }

  name = localStorage.getItem('name');
}
