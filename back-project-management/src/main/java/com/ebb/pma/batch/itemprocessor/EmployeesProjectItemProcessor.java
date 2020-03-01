package com.ebb.pma.batch.itemprocessor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import com.ebb.pma.dao.EmployeeRepository;
import com.ebb.pma.entities.Employee;
import com.ebb.pma.entities.Project;

public class EmployeesProjectItemProcessor implements ItemProcessor<Project, Project>{

	@Autowired EmployeeRepository employeeRepository;
	
	@Override
	public Project process(Project project) throws Exception {
		
		List<Employee> employeesList = new ArrayList<>();
		String strEmployees = project.getStrEmployees();
		String[] tabEmployees = strEmployees.split("\\|");
		for(String strEmployee : tabEmployees) {
			String[] tabEmployee = strEmployee.split(":");
			//Employee employee = new Employee();
			String email = tabEmployee[2];
			Employee employee = employeeRepository.findByEmail(email);
			if(employee == null) {
				employee = new Employee();
				employee.setFirstName(tabEmployee[0]);
				employee.setLastName(tabEmployee[1]);
				employee.setEmail(email);
				employee.setPhone(tabEmployee[3]);
				String gender = tabEmployee[4];
				if(gender.equalsIgnoreCase("female")) {
        			employee.setGender("F");
        		}else if(gender.equalsIgnoreCase("male")) {
        			employee.setGender("M");
        		}
				employee = employeeRepository.save(employee);
			}
			employeesList.add(employee);
		}
		project.setEmployees(employeesList);
		
		return project;
	}

}
