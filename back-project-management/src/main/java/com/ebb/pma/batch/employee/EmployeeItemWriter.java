package com.ebb.pma.batch.employee;

import java.util.List;

import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ebb.pma.dao.EmployeeRepository;
import com.ebb.pma.entities.Employee;

@Component
public class EmployeeItemWriter implements ItemWriter<Employee> {
	
	@Autowired EmployeeRepository employeeRepository;
	
	@Override
	public void write(List<? extends Employee> employees) throws Exception {
		// TODO Auto-generated method stub
		employeeRepository.saveAll(employees);
	}
	
	

}
