import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { Employee } from '../models/employee';
import { Project } from '../models/project';
import { EmployeeService } from '../services/employee.service';
import { ProjectService } from '../services/project.service';
import { Router } from '@angular/router';

interface Stage {
  value: string;
  view: string;
}

interface Gender {
  value: string;
  view: string;
}

@Component({
  selector: 'app-home-page',
  templateUrl: './home-page.component.html',
  styleUrls: ['./home-page.component.scss']
})
export class HomePageComponent implements OnInit {

  employees: Observable<Employee[]>;
  projects: Observable<Project[]>;

  stages: Stage[] = [
    {value: 'NOTSTARTED', view: 'Not Started'},
    {value: 'INPROGRESS', view: 'In Progress'},
    {value: 'COMPLETED', view: 'Completed'}
  ];
  
  genders: Gender[] = [
    {value: 'M', view: 'Male'},
    {value: 'F', view: 'Female'}
  ];

  constructor(private employeeService: EmployeeService, private projectService: ProjectService, private router: Router) { }

  ngOnInit() {
    this.loadData();
  }

  loadData(){
    this.employees = this.employeeService.getEmployeesList();
    this.projects = this.projectService.getProjectsList();
  }

}
