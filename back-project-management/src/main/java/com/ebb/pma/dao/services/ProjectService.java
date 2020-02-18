package com.ebb.pma.dao.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ebb.pma.dao.ProjectRepository;
import com.ebb.pma.dto.ChartData;
import com.ebb.pma.entities.Project;

@Service
public class ProjectService {

	@Autowired
	ProjectRepository projectRepository;
	
	public Project save(Project project) {
		
		return projectRepository.save(project);
	}
	
	public Iterable<Project> getAll() {
		
		return projectRepository.findAll();
	}
	
	public List<ChartData> getProjectStatus() {
		
		return projectRepository.getProjectStatus();
	}
}
