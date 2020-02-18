package com.ebb.pma.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ebb.pma.dao.EmployeeRepository;
import com.ebb.pma.dao.services.ProjectService;
import com.ebb.pma.entities.Employee;
import com.ebb.pma.entities.Project;

@Controller
@RequestMapping("/projects")
public class ProjectController {

	@Autowired
	ProjectService projectService;
	/**ProjectRepository projectRepository;*/
	
	@Autowired
	EmployeeRepository employeeRepository;

	@GetMapping
	public String displayProjects(Model model) {
		// retrieve all projects to database and send its to the view
		 /**List<Project> projects = projectRepository.findAll();*/
		Iterable<Project> projects = projectService.getAll();
		 model.addAttribute("projects", projects);
		 
		 return "projects/list-projects.html";
	}
	
	@GetMapping("/new")
	public String displayProjectForm(Model model) {

		Project aProject = new Project();
		Iterable<Employee> employees = employeeRepository.findAll();
		model.addAttribute("project", aProject);
		model.addAttribute("allEmployees", employees);

		return "projects/new-project";
	}

	@PostMapping("/save")
	public String createProject(Project project, Model model) {
		//this should handle saving to the database...
		/**projectRepository.save(project);*/
		projectService.save(project);
		
		/** Arg of Method: @RequestParam List<Long> employees,
		// update the project for the employee
		Iterable<Employee> chosenEmployees = employeeRepository.findAllById(employees);
		for(Employee employee : chosenEmployees) {
			employee.setProject(project);
			employeeRepository.save(employee);
		}*/
		
		// use redirect to duplicate the submissions
		return "redirect:/projects";
	}
}
