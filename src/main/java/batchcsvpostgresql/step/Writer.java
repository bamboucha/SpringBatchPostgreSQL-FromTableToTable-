package batchcsvpostgresql.step;

import java.util.List;

import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DaoSupport;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import batchcsvpostgresql.dao.CustomerDao;
import batchcsvpostgresql.dao.PersonDao;
import batchcsvpostgresql.model.Customer;
import batchcsvpostgresql.model.CustomerDTO;
import batchcsvpostgresql.model.Person;


// A revoir la méthode des Writer comment c'est fait
public class Writer extends JdbcDaoSupport implements ItemWriter<Person> {

	private StepExecution stepExecution;
	
	@Autowired
	private final PersonDao personDao;

	public Writer(PersonDao personDao) {
		this.personDao = personDao;
	}

	@BeforeStep
    public void saveStepExecution(StepExecution stepExecution) {
        this.stepExecution = stepExecution;
    }
	
	@Override
	public void write(List<? extends Person> items) throws Exception {
		ExecutionContext stepContext = this.stepExecution.getExecutionContext();
        stepContext.put("someKey", items);
		personDao.insert(items);
	}
	
	

}
