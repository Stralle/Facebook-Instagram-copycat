import {Injectable} from '@angular/core';
import {HttpRequest, HttpHandler, HttpEvent, HttpInterceptor} from '@angular/common/http';
import {Observable} from 'rxjs';

@Injectable()
export class JwtInterceptor implements HttpInterceptor {
  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {

    const token = localStorage.getItem('X-AUTH-TOKEN');
    if (token) {

      const headers = request.headers
        .set('X-AUTH-TOKEN', token)
        .set('Content-Type', 'application/json');

      request = request.clone({ headers });
    }

    return next.handle(request);
  }
}
