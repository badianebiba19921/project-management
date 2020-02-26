//import { EmployeeDetailsComponent } from '../employee-details/employee-details.component';
import { Observable } from "rxjs";
import { ProjectService } from "../services/project.service";
import { Project } from "../models/project";
import { Component, OnInit } from "@angular/core";
import { Router } from '@angular/router';

interface Stage {
  value: string;
  view: string;
}

@Component({
  selector: 'app-project-list',
  templateUrl: './project-list.component.html',
  styleUrls: ['./project-list.component.css']
})
export class ProjectListComponent implements OnInit {

  projects: Observable<Project[]>;

  stages: Stage[] = [
    {value: 'NOTSTARTED', view: 'Not Started'},
    {value: 'INPROGRESS', view: 'In Progress'},
    {value: 'COMPLETED', view: 'Completed'}
  ];

  constructor(private projectService: ProjectService, private router: Router) { }

  ngOnInit() {
    this.reloadData();
  }

  reloadData(){
    this.projects = this.projectService.getProjectsList();
  }

  deleteProject(id: number){
    this.projectService.deleteProject(id)
    .subscribe(data=>{
      console.log(data);
      this.reloadData();
    }, error=>console.log(error));
  }

  updateProject(id: number){
    //alert(id);
    this.router.navigate(['updateProject', id]);
  }

  projectDetails(id: number){
    this.router.navigate(['detailsProject', id]);
  }

  addProject(){
    this.router.navigate(['addProject']);
  }
}
