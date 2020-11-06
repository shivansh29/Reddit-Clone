import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { LoginRequest } from '../login/login.request.payload';
import { LoginResponse } from '../login/login.response.payload';
import { signupModel } from '../signup/signup.model';
import { map, tap } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private http: HttpClient) { }
  refreshTokenPayload = {
    refreshToken: this.refreshToken(),
    username: this.getUserName()
  }

  signUp(SignUpModel: signupModel): Observable<any>{
    return this.http.post('http://localhost:8559/api/auth/signup', SignUpModel, { responseType: 'text'});
  }

  login(loginRequestPayload: LoginRequest): Observable<boolean> {
    return this.http.post<LoginResponse>('http://localhost:8559/api/auth/login',
      loginRequestPayload).pipe(map(data => {
        localStorage.setItem('authenticationToken', data.token);
        localStorage.setItem('username', data.username);
        localStorage.setItem('refreshToken', data.refreshToken);
        localStorage.setItem('expiresAt',''+ data.expiresAt);
        return true;
      }));
  }


  getAuthToken(){
    return localStorage.getItem('authenticationToken');
  }


  refreshToken() {
    return this.http.post<LoginResponse>('http://localhost:8559/api/auth/refresh/token',
      this.refreshTokenPayload)
      .pipe(tap(response => {
        localStorage.removeItem('authenticationToken');
        localStorage.removeItem('expiresAt');

        localStorage.setItem('authenticationToken',
          response.token);
        localStorage.setItem('expiresAt', ""+response.expiresAt);
      }));
  }

  getUserName() {
    return localStorage.getItem('username');
  }
}
