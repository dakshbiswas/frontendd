import { Component, OnInit } from '@angular/core';
import { TOKEN_NAME } from '../authentication.service';

@Component({
  selector: 'app-protected',
  templateUrl: './protected.component.html',
  styleUrls: ['./protected.component.css']
})
export class ProtectedComponent implements OnInit {

  constructor() { 
    // console.log("Before");
    // console.log(localStorage.getItem(TOKEN_NAME));
    // console.log("after");
  }

  ngOnInit(): void {
  }
   
    
}
