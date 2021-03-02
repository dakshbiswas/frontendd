import { Component } from '@angular/core';
import { AuthenticationService, TOKEN_NAME } from './authentication.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {

  constructor(private authService: AuthenticationService){
    this.authService.deleteToken
    console.log("token deleted");
    console.log(localStorage.getItem(TOKEN_NAME));
    
  }

  title = 'Job App';
}
