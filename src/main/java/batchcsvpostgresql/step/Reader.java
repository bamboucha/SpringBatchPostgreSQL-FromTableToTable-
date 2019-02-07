package batchcsvpostgresql.step;

import javax.sql.DataSource;

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import batchcsvpostgresql.model.Customer;
import batchcsvpostgresql.model.CustomerDTO;

public class Reader {
	
	 private static final String QUERY_FIND_CUSTOMERS =
	            "SELECT " +
	                "id, " +
	                "first_name, " +
	                "last_name " +
	            "FROM customer " +
	            "ORDER BY id ASC";

	/*public static FlatFileItemReader<Customer> reader(String path) {
		FlatFileItemReader<Customer> reader = new FlatFileItemReader<Customer>();
		reader.setResource(new ClassPathResource(path));
		reader.setLineMapper(new DefaultLineMapper<Customer>() {{
				setLineTokenizer(new DelimitedLineTokenizer() {
					{setNames(new String[] { "id", "firstName", "lastName" });}
				});
		setFieldSetMapper(new BeanWrapperFieldSetMapper<Customer>(){{setTargetType(Customer.class);}
				});
			}
		});
		return reader;
	}*/
	
	public static ItemReader<CustomerDTO> databaseXmlItemReader(DataSource dataSource) {
        JdbcCursorItemReader<CustomerDTO> databaseReader = new JdbcCursorItemReader<>();
        databaseReader.setDataSource(dataSource);
        databaseReader.setSql(QUERY_FIND_CUSTOMERS);
        System.out.println(databaseReader.getDataSource());
        System.out.println(databaseReader.toString());
        databaseReader.setRowMapper(new BeanPropertyRowMapper<>(CustomerDTO.class));
 
        return databaseReader;
    }
}
