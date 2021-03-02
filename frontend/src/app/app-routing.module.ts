import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AuthGuard } from './auth.guard';
import { LoginComponent } from './login/login.component';
import { ProtectedComponent } from './protected/protected.component';
import { RegistrationComponent } from './registration/registration.component';

const routes: Routes = [
  {path: '',component:LoginComponent},
  {path: 'login',component:LoginComponent},
  {path: 'protected',
  component:ProtectedComponent,
  canActivate:[AuthGuard]},
  {path: 'registration',component:RegistrationComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
