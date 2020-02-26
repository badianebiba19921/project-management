import { EmployeeDetailsComponent } from '../employee-details/employee-details.component';
import { Observable } from "rxjs";
import { EmployeeService } from "../services//employee.service";
import { Employee } from "../models/employee";
import { Component, OnInit } from "@angular/core";
import { Router } from '@angular/router';

interface Gender {
  value: string;
  view: string;
}

@Component({
  selector: "app-employee-list",
  templateUrl: "./employee-list.component.html",
  styleUrls: ["./employee-list.component.css"]
})
export class EmployeeListComponent implements OnInit {
  employees: Observable<Employee[]>;

  genders: Gender[] = [
    {value: 'M', view: 'Male'},
    {value: 'F', view: 'Female'}
  ];

  constructor(private employeeService: EmployeeService, private router: Router) {}

  ngOnInit() {
    this.reloadData();
  }

  reloadData() {
    this.employees = this.employeeService.getEmployeesList();
  }

  deleteEmployee(id: number) {
    this.employeeService.deleteEmployee(id)
      .subscribe(
        data => {
          console.log(data);
          this.reloadData();
        },
        error => console.log(error));
  }

  updateEmployee(id: number){
    this.router.navigate(['updateEmployee', id]);
  }

  employeeDetails(id: number){
    this.router.navigate(['detailsEmployee', id]);
  }

  addEmployee(){
    this.router.navigate(['addEmployee']);
  }
}
