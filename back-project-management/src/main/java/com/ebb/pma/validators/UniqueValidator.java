package com.ebb.pma.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.ebb.pma.dao.EmployeeRepository;
import com.ebb.pma.entities.Employee;

public class UniqueValidator implements ConstraintValidator<UniqueValue, String> {

	@Autowired
	EmployeeRepository employeeRepository;
	
	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		
		System.out.println("Entered validation method.");

		Employee employee = employeeRepository.findByEmail(value);
		if(employee != null)
			return false;
		else 
			return true;
		
	}

}
