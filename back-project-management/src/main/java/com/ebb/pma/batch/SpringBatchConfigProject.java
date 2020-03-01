package com.ebb.pma.batch;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.support.CompositeItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import com.ebb.pma.batch.itemprocessor.DomainProjectItemProcessor;
import com.ebb.pma.batch.itemprocessor.EmployeesProjectItemProcessor;
import com.ebb.pma.dao.ProjectRepository;
import com.ebb.pma.entities.Project;

@Configuration
@EnableBatchProcessing
public class SpringBatchConfigProject {

	@Autowired private ProjectRepository projectRepository;
	@Autowired private StepBuilderFactory stepBuilderFactory;
	@Autowired private JobBuilderFactory jobBuilderFactory;
	
	@Bean
	@Qualifier("projectJob")
	public Job projectJob() {
		
		Step step1 = stepBuilderFactory.get("step-project-load-data")
				.<Project, Project> chunk(100)
				.reader(projectItemReader())
				.processor(projectCompositeItemProcessor())
				.writer(projectItemWriter())
				.build();
		
		return jobBuilderFactory.get("project-load-data-job")
				.start(step1)
				.build();
	}
	
	@Bean
    ItemReader<Project> projectItemReader() {
        
        FlatFileItemReader<Project> reader = new FlatFileItemReader<>();
        reader.setResource(new ClassPathResource("projects.csv"));

        DefaultLineMapper<Project> defaultLineMapper = new DefaultLineMapper<Project>();
        DelimitedLineTokenizer delimitedLineTokenizer = new DelimitedLineTokenizer();
        delimitedLineTokenizer.setDelimiter(";");
        delimitedLineTokenizer.setStrict(false);
        delimitedLineTokenizer.setNames(new String[]{"name", "stage", "domain", "description", "strEmployees"});
        //delimitedLineTokenizer.setNames(new String[]{"projectId", "name", "stage", "domain", "description", "strEmployees"});

        BeanWrapperFieldSetMapper<Project> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(Project.class);

        defaultLineMapper.setLineTokenizer(delimitedLineTokenizer);
        defaultLineMapper.setFieldSetMapper(fieldSetMapper);
        reader.setLinesToSkip(1);
        reader.setLineMapper(defaultLineMapper);

        return reader;
    }
	
	//@Bean
	ItemProcessor<Project, Project> projectCompositeItemProcessor(){
		
		List<ItemProcessor<Project, Project>> itemProcessorsList = new ArrayList<>();
		itemProcessorsList.add(itemProcessor1());
		itemProcessorsList.add(itemProcessor2());
		CompositeItemProcessor<Project, Project> compositeItemProcessor = new CompositeItemProcessor<>();
		compositeItemProcessor.setDelegates(itemProcessorsList);
		
		return compositeItemProcessor;
	}
	
	@Bean
	public DomainProjectItemProcessor itemProcessor1() {
		
		return new DomainProjectItemProcessor();
	}
		
	@Bean
	public EmployeesProjectItemProcessor itemProcessor2() {
		
		return new EmployeesProjectItemProcessor();
	}
	
    @Bean
    ItemWriter<Project> projectItemWriter() {
        return new ItemWriter<Project>() {
            @Override
            public void write(List<? extends Project> projects) throws Exception {
            	
            	//projects.forEach(project -> { });
            	projectRepository.saveAll(projects);
            }
        };
    }
	
}
