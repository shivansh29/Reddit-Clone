import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import {  Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { AuthService } from '../shared/auth.service';
import { signupModel } from './signup.model';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css']
})
export class SignupComponent implements OnInit {

  constructor(private authSevice: AuthService, private taostr: ToastrService, private route: Router) { 
    this.SignUpModel = {
      email: '',
      password: '',
      username: ''
    };
  }

  signupForm: FormGroup;
  SignUpModel: signupModel;


  ngOnInit(): void {
    this.signupForm = new FormGroup({
      username: new FormControl('',Validators.required),
      email: new FormControl('', [Validators.required, Validators.email]),
      password: new FormControl('', Validators.required)
    });
  }

  OnSubmit(){
    this.SignUpModel.email = this.signupForm.get('email').value;
    this.SignUpModel.username = this.signupForm.get('username').value;
    this.SignUpModel.password = this.signupForm.get('password').value;

    this.authSevice.signUp(this.SignUpModel).subscribe(
      () => {
        this.route.navigate(['/login'],
          { queryParams: { registered: 'true' } });
      }, () => {
        //console.log();
        this.taostr.error('Registration Failed! Please try again');
      }
    )
  }

}
