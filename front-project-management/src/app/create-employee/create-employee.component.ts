import { EmployeeService } from '../services/employee.service';
import { Employee } from '../models/employee';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

interface Gender {
  value: string;
  view: string;
}

@Component({
  selector: 'app-create-employee',
  templateUrl: './create-employee.component.html',
  styleUrls: ['./create-employee.component.css']
})
export class CreateEmployeeComponent implements OnInit {

  employee: Employee = new Employee();
  submitted = false;

  genders: Gender[] = [
    {value: 'M', view: 'Male'},
    {value: 'F', view: 'Female'}
  ];

  constructor(private employeeService: EmployeeService, private router: Router) { }

  ngOnInit() {}

  newEmployee(): void {
    this.submitted = false;
    this.employee = new Employee();
  }

  save() {
    this.employeeService.createEmployee(this.employee).subscribe(data => console.log(data), error => console.log(error));
    this.employee = new Employee();
    this.gotoList();
  }

  onSubmit() {
    this.submitted = true;
    this.save();
  }

  gotoList() {
    this.router.navigate(['/employees']);
  }
}
