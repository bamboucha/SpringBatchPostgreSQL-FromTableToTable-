package batchcsvpostgresql.dao;

import java.util.List;
import batchcsvpostgresql.model.Person;
import org.springframework.data.repository.CrudRepository;


//Penser a utiliser les JPA respository et les CrudRepository
public interface PersonDao {

	public void insert(List <? extends Person> persons);
	
}
