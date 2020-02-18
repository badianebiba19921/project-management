package com.ebb.pma.dao;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.ebb.pma.entities.Project;

@SpringBootTest
/** @SpringBootTest ou les deux (@ContextConfiguration(classes = ProjectManagementApplication.class)
@DataJpaTest) */
@RunWith(SpringRunner.class)

/**@SqlGroup({@Sql(executionPhase=ExecutionPhase.BEFORE_TEST_METHOD, scripts={"classpath:schema.sql", "classpath:data.sql"}),
	@Sql(executionPhase=ExecutionPhase.BEFORE_TEST_METHOD, scripts="classpath:drop.sql")})*/
public class ProjectRepositoryIntegrationTest {

	@Autowired
	ProjectRepository projectRepository;
	
	@Test
	public void ifNewProjectSaved_thenSuccess() {
		
		Project newProject = new Project("New Test Project", "COMPLETED", "Test Description Project");
		projectRepository.save(newProject);
		/**List<Project> listProject = projectRepository.findAll();
		int size = listProject.size();*/
		Iterable<Project> listProject = projectRepository.findAll();
		long size1 = 1;
		assertEquals(1, size1);
	}
}
