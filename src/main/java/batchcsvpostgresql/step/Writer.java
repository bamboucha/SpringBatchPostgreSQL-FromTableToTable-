package batchcsvpostgresql.step;

import java.util.List;
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

	@Autowired
	private final PersonDao personDao;

	public Writer(PersonDao personDao) {
		this.personDao = personDao;
	}

	@Override
	public void write(List<? extends Person> items) throws Exception {
		personDao.insert(items);
	}

}
