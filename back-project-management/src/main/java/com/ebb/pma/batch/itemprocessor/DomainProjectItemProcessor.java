package com.ebb.pma.batch.itemprocessor;

import org.springframework.batch.item.ItemProcessor;

import com.ebb.pma.entities.Project;

public class DomainProjectItemProcessor implements ItemProcessor<Project, Project>{

	@Override
	public Project process(Project project) throws Exception {

		String domain = project.getDomain();
		if(domain.equals("Decisional Information System")) {
			project.setDomain("BI");
		}else if(domain.equals("IT Networks and Infrastructures")) {
			project.setDomain("NI");
		}else if(domain.equals("IT Application Maintenance")) {
			project.setDomain("AM");
		}else if(domain.equals("Production Deployment")) {
			project.setDomain("PD");
		}else if(domain.equals("Project Budget Management")) {
			project.setDomain("BM");
		}

		return project;
	}

}
