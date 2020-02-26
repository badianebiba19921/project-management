import { Project } from '../models/project';
import { Component, OnInit, Input } from '@angular/core';
import { ProjectService } from '../services/project.service';
//import { EmployeeListComponent } from '../employee-list/employee-list.component';
import { Router, ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-project-details',
  templateUrl: './project-details.component.html',
  styleUrls: ['./project-details.component.css']
})
export class ProjectDetailsComponent implements OnInit {

  id: number;
  project: Project;

  constructor(private route: ActivatedRoute,private router: Router,
    private projectService: ProjectService) { }

  ngOnInit() {
    this.project = new Project();
    this.id = this.route.snapshot.params['id'];
    this.projectService.getProject(this.id)
      .subscribe(data => {
        console.log(data)
        this.project = data;
      }, error => console.log(error));
  }

  list(){
    this.router.navigate(['projects']);
  }

}
