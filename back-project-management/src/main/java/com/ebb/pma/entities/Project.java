package com.ebb.pma.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Project {

	/**@GeneratedValue(strategy = GenerationType.AUTO)
	 * @GeneratedValue(strategy = GenerationType.IDENTITY)*/
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "project_seq")
	@SequenceGenerator(name = "project_seq", sequenceName = "project_seq", allocationSize=1, initialValue=1)
	private long projectId;
	
	private String name;
	private String stage; // NOTSTARTED, COMPETED, INPROGRESS
	
	@Size(min=2, max=2)
	private String domain;
	private String description;
	
	@Transient
	private String strEmployees;
	
	/**@OneToMany(mappedBy="project") 
	 private List<Employee> employees;*/
	
	@ManyToMany(cascade= {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST},
			fetch = FetchType.LAZY)
	@JoinTable(name="project_employee",
		joinColumns = @JoinColumn(name="project_id"),
		inverseJoinColumns = @JoinColumn(name="employee_id")
	)
	@JsonIgnore
	private List<Employee> employees;
	
	public Project() {}
	
	public Project(String name, String stage, @Size(min = 2, max = 2) String domain, String description,
			String strEmployees, List<Employee> employees) {
		super();
		this.name = name;
		this.stage = stage;
		this.domain = domain;
		this.description = description;
		this.strEmployees = strEmployees;
		this.employees = employees;
	}
	
	public List<Employee> getEmployees() {
		return employees;
	}

	public void setEmployees(List<Employee> employees) {
		this.employees = employees;
	}

	public long getProjectId() {
		return projectId;
	}
	
	public void setProjectId(long projectId) {
		this.projectId = projectId;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getStage() {
		return stage;
	}
	
	public void setStage(String stage) {
		this.stage = stage;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public String getStrEmployees() {
		return strEmployees;
	}

	public void setStrEmployees(String strEmployees) {
		this.strEmployees = strEmployees;
	}

	// Convenience method
	public void addEmployee(Employee emp) {
		
		if(employees == null) {
			employees = new ArrayList<>();
		}
		employees.add(emp);
	}

}
