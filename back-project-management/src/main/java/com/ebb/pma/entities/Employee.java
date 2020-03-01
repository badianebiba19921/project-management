package com.ebb.pma.entities;

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
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Employee {
	
	/**@GeneratedValue(strategy = GenerationType.AUTO)
	 * @GeneratedValue(strategy = GenerationType.IDENTITY)*/

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "employee_seq")
	@SequenceGenerator(name = "employee_seq", sequenceName = "employee_seq", allocationSize=1, initialValue=1)
	private long employeeId;
	
	@NotNull
	@Size(min=2, max=50)
	private String firstName;
	
	@NotNull
	@Size(min=1, max=50)
	private String lastName;
	
	//@Column(unique=true)
	//@UniqueValue
	@NotNull
	//@Email
	private String email;
	
	@NotNull
	private String phone;
	
	@NotNull
	//@Size(min=1, max=1)
	private String gender;
	
	/**@ManyToOne(cascade= {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST},
			fetch = FetchType.LAZY)
	@JoinColumn(name="project_id")
	private Project project;*/
	
	@ManyToMany(cascade= {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST},
			fetch = FetchType.LAZY)
	@JoinTable(name="project_employee",
		joinColumns = @JoinColumn(name="employee_id"),
		inverseJoinColumns = @JoinColumn(name="project_id")
	)
	@JsonIgnore
	private List<Project> projects;
	
	public Employee() {}
	
	public Employee(String firstName, String lastName, String email, String phone, String gender) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.phone = phone;
		this.gender = gender;
	}
	
	/**public Employee(long employeeId, @NotNull @Size(min = 2, max = 50) String firstName,
			@NotNull @Size(min = 1, max = 50) String lastName, @NotNull @Email String email, @NotNull String phone,
			@NotNull String gender, List<Project> projects) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.phone = phone;
		this.gender = gender;
		this.projects = projects;
	}*/

	public List<Project> getProjects() {
		return projects;
	}

	public void setProjects(List<Project> projects) {
		this.projects = projects;
	}

	public long getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(long employeeId) {
		this.employeeId = employeeId;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
}
