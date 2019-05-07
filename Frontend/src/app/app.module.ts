import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HeaderComponent } from './header/header.component';
import {HTTP_INTERCEPTORS, HttpClientModule} from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { LoginComponent } from './login/login.component';
import {AuthGuard} from './_guards/auth.guard';
import {JwtInterceptor} from './_helpers/jwt.interceptor';
import { PostsComponent } from './posts/posts.component';
import { ActivateComponent } from './activate/activate.component';
import { ProfileComponent } from './profile/profile.component';
import { UploadComponent } from './upload/upload.component';
import { SearchUsersComponent } from './search-users/search-users.component';
import {SafePipe} from './sanitizer';
import { UserComponent } from './user/user.component';
@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    LoginComponent,
    PostsComponent,
    ActivateComponent,
    ProfileComponent,
    UploadComponent,
    SearchUsersComponent,
    SafePipe,
    UserComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule
  ],
  providers: [
    AuthGuard,
    { provide: HTTP_INTERCEPTORS, useClass: JwtInterceptor, multi: true }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
