package com.ebb.pma.front;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Id;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ebb.pma.dao.EmployeeRepository;
import com.ebb.pma.entities.Employee;
import com.ebb.pma.front.exception.ResourceNotFoundException;

@RestController
@CrossOrigin(origins="http://localhost:4200")
@RequestMapping("/front-app/employees")
public class EmployeeRestController {
	
	@Autowired private EmployeeRepository employeeRepository;

	@GetMapping
	public List<Employee> getAllEmployees(){
		
		return employeeRepository.findAll();
	}
	
	@GetMapping("/{employeeId}")
	public ResponseEntity<Employee> getProjectById(@Valid @PathVariable(value="employeeId") long employeeId) throws ResourceNotFoundException {
		
		Employee employee = employeeRepository.findById(employeeId)
				.orElseThrow(()-> new ResourceNotFoundException("Employee not found for this EmployeeId :: " + employeeId));
		
		return ResponseEntity.ok().body(employee);
	}
	
	@PostMapping
	public Employee createEmployee(@Valid @RequestBody Employee employee) {
		
		return employeeRepository.save(employee);
	}
	
	@PutMapping("/{employeeId}")
	public ResponseEntity<Employee> updateEmployee(@Valid @RequestParam(value="employeeId") long employeeId, @Valid @RequestBody Employee employeeDetails) throws ResourceNotFoundException {
		
		Employee employee = employeeRepository.findById(employeeId)
				.orElseThrow(()-> new ResourceNotFoundException("Employee not found for this EmployeeId :: " + employeeId));
		employee.setFirstName(employeeDetails.getFirstName());
		employee.setLastName(employeeDetails.getLastName());
		employee.setEmail(employeeDetails.getEmail());
		employee.setPhone(employeeDetails.getPhone());
		employee.setGender(employeeDetails.getGender());
		Employee updatedEmployee = employeeRepository.save(employee);
		
		return ResponseEntity.ok().body(updatedEmployee);
	}
	
	@DeleteMapping("/{employeeId}")
	public Map<String, Boolean> deleteEmployee(@Valid @PathVariable("employeeId") long employeeId) throws ResourceNotFoundException {
		
		Employee employee = employeeRepository.findById(employeeId)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not found for this EmployeeId :: " + employeeId));
		
		employeeRepository.delete(employee);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		
		return response;
	}
}
