package com.ebb.pma.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.ebb.pma.dao.EmployeeRepository;
import com.ebb.pma.dao.services.EmployeeService;
import com.ebb.pma.entities.Employee;


@Controller
@RequestMapping("/employees")
public class EmployeeController {

	@Autowired EmployeeService employeeService;
	@Autowired EmployeeRepository employeeRepository;
	@Autowired JobLauncher jobLauncher;
	@Autowired Job job;
	
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
		employeeRepository.save(employee);
//		employeeService.save(employee);

		// use redirect to duplicate the submissions
		return "redirect:/employees";
	}
	
	@GetMapping("/batch")
	public String batchLuncher(Model model) {
		
		return "employees/employee-batch";		
	}
	
	@PostMapping
	public String importFileAndLunchJob(@RequestParam("myFile") MultipartFile file, Model model) throws Exception {
		
		String fileName = file.getOriginalFilename();
		if(!fileName.toLowerCase().endsWith("csv")) {
			model.addAttribute("messageFormat", "Le fichier à traiter doit impérativement être au format csv.");
			
			return "redirect:/employees/batch";
		}
		
		Map<String, JobParameter> parameters = new HashMap();
		parameters.put("time", new JobParameter(System.currentTimeMillis()));
		parameters.put("fileName", new JobParameter(fileName));
		JobParameters jobParameters = new JobParameters(parameters);
		JobExecution jobExecution = jobLauncher.run(job, jobParameters);
		
		while(jobExecution.isRunning()) {
			System.out.println("....." + jobExecution.getStatus() + ".....");
		}
		
		return "redirect:/employees";
	}
}
