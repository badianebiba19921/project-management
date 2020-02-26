import { Component, OnInit } from '@angular/core';
//import { MatSelectModule } from '@angular/material/select';
import { ProjectService } from '../services/project.service';
import { Project } from '../models//project';
import { Router } from '@angular/router';

interface Stage {
  value: string;
  view: string;
}

@Component({
  selector: 'app-create-project',
  templateUrl: './create-project.component.html',
  styleUrls: ['./create-project.component.css']
})
export class CreateProjectComponent implements OnInit {

  project: Project = new Project();
  submitted = false;

  stages: Stage[] = [
    {value: 'NOTSTARTED', view: 'Not Started'},
    {value: 'INPROGRESS', view: 'In Progress'},
    {value: 'COMPLETED', view: 'Completed'}
  ];

  constructor(private projectService: ProjectService, private router: Router) { }

  ngOnInit() {}

  newProject():void {
    this.submitted = false;
    this.project = new Project();
  }

  save() {
    this.projectService.createProject(this.project).subscribe(data => console.log(data), error => console.log(error));
    this.project = new Project();
    this.gotoList();
  }

  onSubmit() {
    this.submitted = true;
    this.save();
  }

  gotoList() {
    this.router.navigate(['/projects']);
  }

}
