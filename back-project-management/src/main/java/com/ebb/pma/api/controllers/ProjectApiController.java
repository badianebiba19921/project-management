package com.ebb.pma.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.ebb.pma.dao.ProjectRepository;
import com.ebb.pma.entities.Project;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/app-api/projects")
public class ProjectApiController {
	
	@Autowired
	ProjectRepository projectRepository;

	@GetMapping
	public Iterable<Project> getProjetcs() {
		return projectRepository.findAll();
	}
	
	@GetMapping("/{id}")
	public Project getProjectById(@PathVariable("id") long id) {
		return projectRepository.findById(id).get();
	}
	
	@PostMapping(consumes="application/json")
	@ResponseStatus(HttpStatus.CREATED) // 201 Created.
	public Project create(@RequestBody Project project) {
		return projectRepository.save(project);
	}
	
	@PutMapping(consumes="application/json")
	@ResponseStatus(HttpStatus.OK) // 200 OK
	public Project updateProject(@RequestBody Project project) {
		return projectRepository.save(project);
	}
	
	@PatchMapping(path="/{id}", consumes="application/json")
	public Project updatePartialProject(@PathVariable("id") long id, @RequestBody Project patchProject) {
		Project project = projectRepository.findById(id).get();
		
		if(patchProject.getName() != null) {
			project.setName(patchProject.getName());
		}
		if(patchProject.getStage() != null) {
			project.setStage(patchProject.getStage());
		}
		if(patchProject.getDescription() != null) {
			project.setDescription(patchProject.getDescription());
		}
		
		return projectRepository.save(project);		
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT) // 204 No Content.
	public void deleteProject(@PathVariable("id") long id) {
		/**projectRepository.delete(project);*/
		
		try {
			projectRepository.deleteById(id);
		}catch (EmptyResultDataAccessException e) {}
	}
}
