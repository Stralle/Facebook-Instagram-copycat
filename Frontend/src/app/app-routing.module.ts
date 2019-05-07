import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {LoginComponent} from './login/login.component';
import {AuthGuard} from './_guards/auth.guard';
import {PostsComponent} from './posts/posts.component';
import {ActivateComponent} from './activate/activate.component';
import {ProfileComponent} from './profile/profile.component';
import {UploadComponent} from './upload/upload.component';
import {SearchUsersComponent} from './search-users/search-users.component';
import {UserComponent} from './user/user.component';

const routes: Routes = [
  { path: 'home', component: PostsComponent, canActivate: [AuthGuard] },
  { path: 'login', component: LoginComponent },
  { path: 'activate', component: ActivateComponent},
  { path: 'profile', component: ProfileComponent, canActivate: [AuthGuard] },
  { path: 'user', component: UserComponent, canActivate: [AuthGuard] },
  { path: 'search-users', component: SearchUsersComponent, canActivate: [AuthGuard] },
  { path: 'upload', component: UploadComponent, canActivate: [AuthGuard] },
  { path: '', redirectTo: '/login', pathMatch: 'full', canActivate: [AuthGuard]},
  { path: '**', redirectTo: '' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
