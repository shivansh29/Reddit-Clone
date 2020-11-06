import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { throwError } from 'rxjs';
import { AuthService } from '../shared/auth.service';
import { LoginRequest } from './login.request.payload';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  constructor(private authService: AuthService,private taostr: ToastrService, 
                private route: Router,private activateRoute: ActivatedRoute) {
    this.loginRequestPayload = {
      username: '',
      password: ''
    };
  }
  

  loginForm: FormGroup;
  loginRequestPayload: LoginRequest;
  registerSuccessMessage: string;
  isError: boolean;


  ngOnInit(): void {
    this.loginForm = new FormGroup({
      username : new FormControl('', Validators.required),
      password: new FormControl('', Validators.required)
    });

    this.activateRoute.queryParams.subscribe(
      params =>{
        if(params.registered !== undefined && params.registered ==='true'){
          this.taostr.success('SignUp Successfull');
          this.registerSuccessMessage = 'Please Check your inbox for activation email '
          + 'activate your account before you Login!';
        }
      }
    )
  }

  login(){
    this.loginRequestPayload.username = this.loginForm.get('username').value;
    this.loginRequestPayload.password = this.loginForm.get('password').value;

    this.authService.login(this.loginRequestPayload).subscribe(
      data =>{
            this.isError = false;
            this.route.navigateByUrl('');
            this.taostr.success('Login Successful');
      },
      error =>{
        this.isError = true;
      throwError(error);
      }
    );
  }
}
