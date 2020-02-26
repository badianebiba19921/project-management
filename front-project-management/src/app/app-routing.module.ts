import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { EmployeeDetailsComponent } from './employee-details/employee-details.component';
import { CreateEmployeeComponent } from './create-employee/create-employee.component';
import { CreateProjectComponent } from './create-project/create-project.component';
import { EmployeeListComponent } from './employee-list/employee-list.component';
import { ProjectListComponent } from './project-list/project-list.component';
import { UpdateEmployeeComponent } from './update-employee/update-employee.component';
import { UpdateProjectComponent } from './update-project/update-project.component';
import { ProjectDetailsComponent } from './project-details/project-details.component';
import { HomePageComponent } from './home-page/home-page.component';


const routes: Routes = [
  { path: '', redirectTo: 'home', pathMatch: 'full' },
  { path: 'home', component: HomePageComponent },
  { path: 'employees', component: EmployeeListComponent },
  { path: 'projects', component: ProjectListComponent },
  { path: 'addEmployee', component: CreateEmployeeComponent },
  { path: 'addProject', component: CreateProjectComponent },
  { path: 'detailsEmployee/:id', component: EmployeeDetailsComponent },
  { path: 'detailsProject/:id', component: ProjectDetailsComponent },
  { path: 'updateEmployee/:id', component: UpdateEmployeeComponent },
  { path: 'updateProject/:id', component: UpdateProjectComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
