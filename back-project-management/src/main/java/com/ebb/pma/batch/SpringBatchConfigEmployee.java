package com.ebb.pma.batch;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersIncrementer;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

import com.ebb.pma.entities.Employee;
@Configuration
@EnableBatchProcessing
public class SpringBatchConfigEmployee {
	
	@Autowired private JobBuilderFactory jobBuilderFactory;
	@Autowired private StepBuilderFactory stepBuilderFactory;
	//@Autowired private ItemReader<Employee> employeeItemReader;
	@Autowired private ItemProcessor<Employee, Employee> employeeItemProcessor;
	@Autowired private ItemWriter<Employee> employeeItemWriter;
	
	@Bean
	public Job employeeJob() {
		
		Step step1 = stepBuilderFactory.get("step-employee-load-data")
				.<Employee, Employee> chunk(100)
				.reader(employeeItemReader())
				.processor(employeeItemProcessor)
				.writer(employeeItemWriter)
				.build();
		
		return jobBuilderFactory.get("employee-data-loader-job")
				//.incrementer(new RunIdIncrementer())
				.start(step1)
				.build();
	}
	
	@Bean
    ItemReader<Employee> employeeItemReader() {
        
        FlatFileItemReader<Employee> reader = new FlatFileItemReader<>();
        reader.setResource(new ClassPathResource("employees.csv"));

        DefaultLineMapper<Employee> defaultLineMapper = new DefaultLineMapper<Employee>();
        DelimitedLineTokenizer delimitedLineTokenizer = new DelimitedLineTokenizer();
        delimitedLineTokenizer.setNames(new String[]{"employeeId", "firstname", "lastname", "email", "phone", "gender"});

        BeanWrapperFieldSetMapper<Employee> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(Employee.class);

        defaultLineMapper.setLineTokenizer(delimitedLineTokenizer);
        defaultLineMapper.setFieldSetMapper(fieldSetMapper);
        reader.setLineMapper(defaultLineMapper);

        return reader;
    }
	
	/**@Bean
	//@StepScope
	public FlatFileItemReader<Employee> flatFileItemReader(@Value("${inputFile}") Resource inputFile){//(@Value("#{jobParameters['fileName']}") String fileName){
		
		FlatFileItemReader<Employee> flatFileItemReader = new FlatFileItemReader<>();
		flatFileItemReader.setName("FFIR-Employee");
		flatFileItemReader.setLinesToSkip(1);
		flatFileItemReader.setResource(inputFile);  //(new FileSystemResource(fileName));
		flatFileItemReader.setLineMapper(lineMapper());
		
		return flatFileItemReader;
	}

	@Bean
	public LineMapper<Employee> lineMapper() {
		
		DelimitedLineTokenizer delimitedLineTokenizer = new DelimitedLineTokenizer();
		delimitedLineTokenizer.setDelimiter(",");
		delimitedLineTokenizer.setStrict(false);
		delimitedLineTokenizer.setNames(new String[]{"firstname", "lastname", "email", "phone", "gender"});
		
		BeanWrapperFieldSetMapper<Employee> beanWrapperFieldSetMapper = new BeanWrapperFieldSetMapper<>();
		beanWrapperFieldSetMapper.setTargetType(Employee.class);
		
		DefaultLineMapper<Employee> defaultLineMapper = new DefaultLineMapper<>();
		defaultLineMapper.setLineTokenizer(delimitedLineTokenizer);
		defaultLineMapper.setFieldSetMapper(beanWrapperFieldSetMapper);
		
		return defaultLineMapper;
	}*/
	
}
