package batchcsvpostgresql.config;

import java.util.List;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import batchcsvpostgresql.dao.CustomerDao;
import batchcsvpostgresql.dao.PersonDao;
import batchcsvpostgresql.model.Customer;
import batchcsvpostgresql.model.CustomerDTO;
import batchcsvpostgresql.model.Person;
import batchcsvpostgresql.step.Listener;
import batchcsvpostgresql.step.Processor;
import batchcsvpostgresql.step.Reader;
import batchcsvpostgresql.step.Writer;

@Configuration
@EnableBatchProcessing
public class BatchConfig {
private static final Logger log = LoggerFactory.getLogger(BatchConfig.class);
	
	final String QUERY_FIND_CUSTOMERS =  "SELECT " +
            "id, " +
            "first_name, " +
            "last_name " +
        "FROM customer " +
        "ORDER BY id ASC";


	@Autowired
	DataSource dataSource;
	
	//Job Repo
	@Autowired
	public JobBuilderFactory jobBuilderFactory;

	//Job Repo avec transaction management 
	@Autowired
	public StepBuilderFactory stepBuilderFactory;

	@Autowired
	public PersonDao personDao;

	@Autowired
	JobLauncher jobLauncher;

	@Bean
	public Job job() {

	final Job jobFromDB =  jobBuilderFactory.get("job")
			.incrementer(new RunIdIncrementer())
			.listener(new Listener())
			.flow(step1()).end().build();

		//Si je veux éxécuter je passe par le job launcher 
		JobParameters jobParameters = new JobParametersBuilder().addLong("time", System.currentTimeMillis())
				.toJobParameters();
		try {
			jobLauncher.run(jobFromDB, jobParameters);
		} catch (JobExecutionAlreadyRunningException e) {
			e.printStackTrace();
		} catch (JobRestartException e) {
			e.printStackTrace();
		} catch (JobInstanceAlreadyCompleteException e) {
			e.printStackTrace();
		} catch (JobParametersInvalidException e) {
			e.printStackTrace();
		}
		return jobFromDB;
}

	@Bean
	public Step step1() {
		return 
				stepBuilderFactory.get("step1").<CustomerDTO, Person>chunk(2)
				
				.reader(Reader.databaseXmlItemReader(dataSource))
				
				.processor(new Processor()).writer(new Writer(personDao)).build();
	}



}
