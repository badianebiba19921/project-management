package com.ebb.pma.controllers;

import java.util.Arrays;
import java.util.List;

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
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.ebb.pma.batch.CustomJobParameter;
import com.ebb.pma.dao.EmployeeRepository;
import com.ebb.pma.dao.services.EmployeeService;
import com.ebb.pma.entities.Employee;
import com.ebb.pma.entities.Message;


@Controller
@RequestMapping("/employees")
public class EmployeeController {

	@Autowired EmployeeService employeeService;
	@Autowired EmployeeRepository employeeRepository;
	@Autowired JobLauncher jobLauncher;
	@Autowired @Qualifier("employeeJob") Job job;
	
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
		// employeeService.save(employee);

		// use redirect to duplicate the submissions
		return "redirect:/employees";
	}
	
	@GetMapping("/batch")
	public String batchLuncher(ModelMap modelMap) {
		
		List<Message> messages = Arrays.asList();
		if(modelMap.getAttribute("messageFormat") != null) {
			Message message = new Message((String) modelMap.getAttribute("messageFormat"));
			messages.add(message);
		}
		modelMap.addAttribute("messages", messages);
		
		return "employees/employee-batch";		
	}
	
	@PostMapping("/run-batch")
	public ModelAndView importFileAndLunchJob(@RequestParam("myFile") MultipartFile file, ModelMap modelMap) throws Exception {
		
		String filename = file.getOriginalFilename();
		if(!filename.toLowerCase().endsWith("csv")) {
			modelMap.addAttribute("messageFormat", "Le fichier à traiter doit impérativement être au format csv.");
			
	        return new ModelAndView("redirect:/employees/batch", modelMap);
		}
		
		/** Map<String, JobParameter> parameters = new HashMap<String, JobParameter>();
		parameters.put("time", new JobParameter(System.currentTimeMillis()));
		parameters.put("fileName", new JobParameter(filename));
		JobParameters jobParameters = new JobParameters(parameters);*/
		
		JobParameters jobParameters = new JobParametersBuilder()
				.addParameter("time", new JobParameter(System.currentTimeMillis()))
				.addParameter("filename", new JobParameter(filename))
				.addParameter("file", new CustomJobParameter(file))
				.toJobParameters();
		
		JobExecution jobExecution = jobLauncher.run(job, jobParameters);
		
		while(jobExecution.isRunning()) {
			System.out.println("....." + jobExecution.getStatus() + ".....");
		}
		return new ModelAndView("redirect:/employees");
	}
}
