package batchcsvpostgresql.dao.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;
import javax.sql.DataSource;
import batchcsvpostgresql.dao.PersonDao;
import batchcsvpostgresql.model.Customer;
import batchcsvpostgresql.model.Person;


@Repository
public class PersonDaoImpl extends JdbcDaoSupport implements PersonDao{

	@Autowired
	DataSource dataSource;
	
	@PostConstruct
	private void initialize() {
		setDataSource(dataSource);
	}
	
	@Override
	public void insert(List<? extends Person> Persons) {
		
		String sql = "INSERT INTO person (id, first_name, last_name) VALUES (?, ?, ?)";
		getJdbcTemplate().batchUpdate(sql, new BatchPreparedStatementSetter() {
			
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				
				Person person = Persons.get(i);
				ps.setLong(1, person.getId());
				ps.setString(2, person.getFirstName());
				ps.setString(3, person.getLastName());
			}
			
			public int getBatchSize() {
				return Persons.size();
			}
		});			
	}

}
