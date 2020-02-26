package com.ebb.pma.batch.employee;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import com.ebb.pma.entities.Employee;

@Component
public class EmployeeItemProcessor implements ItemProcessor<Employee, Employee> {

	@Override
	public Employee process(Employee employee) throws Exception {
		// TODO Auto-generated method stub
		String gender = employee.getGender();
		if(gender.equalsIgnoreCase("female")) {
			employee.setGender("F");
		}else if(gender.equalsIgnoreCase("male")) {
			employee.setGender("M");
		}
		return employee;
	}

}
