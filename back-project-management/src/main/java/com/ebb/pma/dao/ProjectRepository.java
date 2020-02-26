package com.ebb.pma.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.ebb.pma.dto.ChartData;
import com.ebb.pma.entities.Project;

public interface ProjectRepository extends PagingAndSortingRepository<Project, Long>{ /** extends CrudRepository<Project, Long>{ */
	
	/**@Override
	public List<Project> findAll();*/
	
	@Query(nativeQuery=true, value="SELECT STAGE AS  LABEL, COUNT(*) AS VALUE FROM PROJECT GROUP BY STAGE")
	public List<ChartData> getProjectStatus();

	public List<Project> findAll();
	
}
