package com.ebb.pma.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.ebb.pma.dto.EmployeeProject;
import com.ebb.pma.entities.Employee;


/**@Repository
@Profile("prod")*/
public interface EmployeeRepository extends PagingAndSortingRepository<Employee, Long>{ /** extends CrudRepository<Employee, Long>{ */

	/**@Override
	public List<Employee> findAll();*/
	
	@Query(nativeQuery=true, value="SELECT FIRST_NAME AS  FIRSTNAME, LAST_NAME AS  LASTNAME, COUNT(PE.EMPLOYEE_ID) AS PROJECTCOUNT " + 
			"FROM EMPLOYEE E LEFT JOIN PROJECT_EMPLOYEE PE ON E.EMPLOYEE_ID =PE.EMPLOYEE_ID " + 
			"GROUP BY FIRST_NAME, LAST_NAME " + 
			"ORDER BY 3 DESC")
	public List<EmployeeProject> employeeProjects();

	public Employee findByEmail(String value);
	
}
