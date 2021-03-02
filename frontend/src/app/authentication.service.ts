import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs/';
import * as jwt_decode from 'jwt-decode';
import { User } from './user';

export const TOKEN_NAME:string = "jwt_token";

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {
  authServiceEndpoint:string = "http://localhost:8080";
  token:string;

  constructor(private http: HttpClient) { }

  registerUser(newUser:User) {
    const url = `${this.authServiceEndpoint}/register`;
    return this.http.post(url, newUser, {responseType: 'text'});
  }

  loginUser(user:User):Observable<any> {
    const url = `${this.authServiceEndpoint}/login`;
    return this.http.post(url, user);
  }

  setToken(token:string) {
    return localStorage.setItem(TOKEN_NAME, token);
  }

  getToken() {
    return localStorage.getItem(TOKEN_NAME);
  }

  deleteToken() {
    return localStorage.removeItem(TOKEN_NAME);
  }
  isLoggedIn(){
    let token = localStorage.getItem(TOKEN_NAME);
    if(token==undefined || token==='' || token==null){
        return false;
    } else 
    return true;
  }

  logout(){
    localStorage.removeItem(TOKEN_NAME);
    return true;
  }
  setName(name:string) {
    return localStorage.setItem("name", name);
  }
}
