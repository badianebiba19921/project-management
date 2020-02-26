package com.ebb.pma.front;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.ebb.pma.dao.ProjectRepository;
import com.ebb.pma.entities.Project;
import com.ebb.pma.front.exception.ResourceNotFoundException;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/front-app/projects")
public class ProjectRestController {
	
	@Autowired private ProjectRepository projectRepository;

	@GetMapping
	public List<Project> getAllProjects(){
		
		return projectRepository.findAll();
	}
	
	@GetMapping("/{projectId}")
	public ResponseEntity<Project> getProjectById(@PathVariable(value="projectId") long projectId) throws ResourceNotFoundException {
		
		Project project = projectRepository.findById(projectId)
				.orElseThrow(()-> new ResourceNotFoundException("Project not found for this id :: " + projectId));
		
		return ResponseEntity.ok().body(project);
	}
	
	@PostMapping
	public Project createProject(@Valid @RequestBody Project project){
		
		return projectRepository.save(project);
	}
	
	@PutMapping("/{projectId}")
	public ResponseEntity<Project> updateEmployee(@Valid @RequestParam(value="projectId") long projectId, @Valid @RequestBody Project projectDetails) throws ResourceNotFoundException {
		
		Project project = projectRepository.findById(projectId)
				.orElseThrow(()-> new ResourceNotFoundException("Project not found for this Project Id :: " + projectId));
		project.setName(projectDetails.getName());
		project.setStage(projectDetails.getStage());
		project.setDescription(projectDetails.getDescription());
		Project updateProject = projectRepository.save(project);
		
		return ResponseEntity.ok().body(updateProject);
	}
	
	@DeleteMapping("/{projectId}")
	public Map<String, Boolean> daleteProject(@Valid @PathVariable(value="projectId") long projectId) throws ResourceNotFoundException {
		
		Project project = projectRepository.findById(projectId)
				.orElseThrow(() -> new ResourceNotFoundException("Project not found for this Project Id :: " + projectId));
		
		projectRepository.delete(project);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		
		return response;
	}
	
}
