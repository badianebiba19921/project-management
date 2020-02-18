package com.ebb.pma.dao.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ebb.pma.dao.EmployeeRepository;
import com.ebb.pma.dto.EmployeeProject;
import com.ebb.pma.entities.Employee;

@Service //(@Component,@Repository)
public class EmployeeService {

	// Field Injection
	@Autowired
	EmployeeRepository employeeRepository;

	public Employee save(Employee employee) {
		
		return employeeRepository.save(employee);
	}
	
	public Iterable<Employee> getAll(){
		
		return employeeRepository.findAll();
	}
	
	public List<EmployeeProject> employeeProjects(){
		
		return employeeRepository.employeeProjects();
	}
	
	// Construtor Injection
	/**@Autowired
	public void setEmployeeRepository(EmployeeRepository employeeRepository) {
		this.employeeRepository = employeeRepository;
	}*/

	// Construtor Injection
	/**public EmployeeService(EmployeeRepository employeeRepository) {
		super();
		this.employeeRepository = employeeRepository;
	}*/
	
	
	
}
