package com.ebb.pma.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.ebb.pma.dao.services.EmployeeService;
import com.ebb.pma.dao.services.ProjectService;
import com.ebb.pma.dto.ChartData;
import com.ebb.pma.dto.EmployeeProject;
import com.ebb.pma.entities.Project;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


@Controller
public class HomeController {

	@Value("${version}")
	String version;
	
	@Autowired
	ProjectService projectService;
	/**ProjectRepository projectRepository;*/
	
	@Autowired
	EmployeeService employeeService;
	/**EmployeeRepository employeeRepository;*/

	@GetMapping("/")
	public String displayHome(Model model) throws JsonProcessingException {
		
		//Map<String, Object> map = new HashMap<>();
		
		model.addAttribute("version", version);
		
		// retrieve the list of projects on the database
		/**List<Project> projects = projectRepository.findAll();*/
		Iterable<Project> projects = projectService.getAll();
		model.addAttribute("projectsList", projects);

		/**List<ChartData> projectData = projectRepository.getProjectStatus();*/
		List<ChartData> projectData = projectService.getProjectStatus();
		// Lets convert projectData into a json structure for use in javascript
		ObjectMapper objectMapper = new ObjectMapper();
		String jsonString = objectMapper.writeValueAsString(projectData);
		// [{NOTSTARTED, 1}, {INPROGRESS, 2}, {COMPLETED,	1}]
		
		model.addAttribute("projectStatusCount", jsonString);
		
		// retrieve the list of employees on the database
		/**List<Employee> employees = employeeRepository.findAll();
		model.addAttribute("employeesList", employees);*/
		
		// retrieve the list of employeeProjects on the database
		/**List<EmployeeProject> employeeProjects = employeeRepository.employeeProjects();*/
		List<EmployeeProject> employeeProjects = employeeService.employeeProjects();
		model.addAttribute("employeeProjectsList", employeeProjects);

		return "main/home";
	}
}
