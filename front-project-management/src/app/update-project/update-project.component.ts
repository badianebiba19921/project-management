import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ProjectService } from '../services/project.service';
import { Project } from '../models/project';

@Component({
  selector: 'app-update-project',
  templateUrl: './update-project.component.html',
  styleUrls: ['./update-project.component.css']
})
export class UpdateProjectComponent implements OnInit {

  id: number;
  project: Project;

  constructor(private route: ActivatedRoute, private router: Router, private projectService: ProjectService) { }

  ngOnInit() {
    this.project = new Project();
    this.id = this.route.snapshot.params['id'];
    this.projectService.getProject(this.id)
    .subscribe(data=>{
        console.log(data)
        this.project = data;
      },error=>console.log(error));
  }

  updateProject(){
    this.projectService.updateProject(this.id, this.project)
    .subscribe(data => console.log(data), error => console.log(error));
    this.project = new Project();
    this.gotoList();
  }

  onSubmit() {
    this.updateProject();
  }

  gotoList() {
    this.router.navigate(['/projects']);
  }

}
