import { HttpErrorResponse, HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable, throwError } from 'rxjs';
import { catchError, filter, switchMap, take } from 'rxjs/operators';
import { LoginResponse } from './auth/login/login.response.payload';
import { AuthService } from './auth/shared/auth.service';

@Injectable({ providedIn: 'root' })
export class TokenInterceptor implements HttpInterceptor{

    constructor(private authService: AuthService){}

    isTokenRefreshing = false;
    refreshTokenSubject: BehaviorSubject<any> = new BehaviorSubject(null);

    intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>>{

        const token=this.authService.getAuthToken();

        if(token){
            this.addToken(req, token);
        }
         return next.handle(this.addToken(req, token)).pipe(catchError(error => {
            if (error instanceof HttpErrorResponse
                && error.status === 403) {
                return this.handleAuthErrors(req, next);
            } else {
                return throwError(error);
            }
        }));
    }

    addToken(req: HttpRequest<any>, jwtToken: any){
        return req.clone({
            headers: req.headers.set('Authorization','Bearer '+jwtToken)
        });
    }


    private handleAuthErrors(req: HttpRequest<any>, next: HttpHandler)
        : Observable<HttpEvent<any>> {
        if (!this.isTokenRefreshing) {
            this.isTokenRefreshing = true;
            this.refreshTokenSubject.next(null);

            return this.authService.refreshToken().pipe(
                switchMap((refreshTokenResponse: LoginResponse) => {
                    this.isTokenRefreshing = false;
                    this.refreshTokenSubject
                        .next(refreshTokenResponse.token);
                    return next.handle(this.addToken(req,
                        refreshTokenResponse.token));
                })
            )
        } else {
            return this.refreshTokenSubject.pipe(
                filter(result => result !== null),
                take(1),
                switchMap((res) => {
                    return next.handle(this.addToken(req,
                        this.authService.getAuthToken()))
                })
            );
        }
    }
}