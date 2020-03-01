package com.ebb.pma.controllers;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.ebb.pma.batch.CustomJobParameter;
import com.ebb.pma.dao.EmployeeRepository;
import com.ebb.pma.dao.services.ProjectService;
import com.ebb.pma.entities.Employee;
import com.ebb.pma.entities.Project;
import com.ebb.pma.entities.project.DomainDAO;

@Controller
@RequestMapping("/projects")
public class ProjectController {

	@Autowired ProjectService projectService;
	@Autowired DomainDAO domainDAO;
	@Autowired JobLauncher jobLauncher;
	@Autowired @Qualifier("projectJob") Job job;
	
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
	    model.addAttribute("mapDomains", domainDAO.getMapDomains());

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
	
	@GetMapping("/batch")
	public String batchLuncher(Model model) {
		
		return "projects/project-batch";		
	}
	
	@PostMapping("/run-batch")
	public String importFileAndLunchJob(@RequestParam("myFile") MultipartFile file, Model model) throws Exception {
		
		String filename = file.getOriginalFilename();
		if(!filename.toLowerCase().endsWith("csv")) {
			model.addAttribute("messageFormat", "Le fichier à traiter doit impérativement être au format csv.");
			return "redirect:/projects/batch";
		}
		JobParameters jobParameters = new JobParametersBuilder()
				.addParameter("time", new JobParameter(System.currentTimeMillis()))
				.addParameter("filename", new JobParameter(filename))
				.addParameter("file", new CustomJobParameter(file))
				.toJobParameters();
		
		JobExecution jobExecution = jobLauncher.run(job, jobParameters);
		while(jobExecution.isRunning()) {
			System.out.println("....." + jobExecution.getStatus() + ".....");
		}
		return "redirect:/projects";
	}
}
