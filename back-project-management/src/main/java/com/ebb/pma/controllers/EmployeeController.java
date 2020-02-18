package com.ebb.pma.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ebb.pma.dao.services.EmployeeService;
import com.ebb.pma.entities.Employee;


@Controller
@RequestMapping("/employees")
public class EmployeeController {

	@Autowired
	EmployeeService employeeService;
	/**EmployeeRepository employeeRepository;*/
	
	@GetMapping
	public String displayEmployees(Model model) {
		// retrieve all employees to database and send its to the view
		 /**List<Employee> employees = employeeRepository.findAll();
		  * List<Employee> employees = employeeRepository.findAll();*/
		Iterable<Employee> employees = employeeService.getAll();
		model.addAttribute("employees", employees);
		 
		 return "employees/list-employees.html";
	}
	
	@GetMapping("/new")
	public String displayEmployeeForm(Model model) {

		Employee anEmployee = new Employee();
		model.addAttribute("employee", anEmployee);

		return "employees/new-employee";
	}

	@PostMapping("/save")
	public String createProject(Employee employee, Model model) {
		//this should handle saving to the database...
		/**employeeRepository.save(employee);*/
		employeeService.save(employee);

		// use redirect to duplicate the submissions
		return "redirect:/employees";
	}
}
