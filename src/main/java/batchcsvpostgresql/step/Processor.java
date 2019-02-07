package batchcsvpostgresql.step;

import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

import batchcsvpostgresql.model.Customer;
import batchcsvpostgresql.model.CustomerDTO;
import batchcsvpostgresql.model.Person;

public class Processor implements ItemProcessor<CustomerDTO, Person> {

	private static final Logger log = LoggerFactory.getLogger(Processor.class);

	@Override
	public Person process(CustomerDTO customer) throws Exception {
		Random r = new Random();
		
		final String firstName = customer.getFirstName().toUpperCase();
		final String lastName = customer.getLastName().toUpperCase();

		final Person fixedCustomer = new Person(r.nextLong(), firstName, lastName);

		log.info("Converting (" + customer + ") into (" + fixedCustomer + ")");

		return fixedCustomer;
	}
}
