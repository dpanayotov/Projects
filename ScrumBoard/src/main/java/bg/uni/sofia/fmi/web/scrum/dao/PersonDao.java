package bg.uni.sofia.fmi.web.scrum.dao;

import bg.uni.sofia.fmi.web.scrum.model.Person;
import bg.uni.sofia.fmi.web.scrum.persistence.EntityManagerProvider;

public class PersonDao extends AbstractDao<Long, Person> {

	public PersonDao() {
		super(Person.class, EntityManagerProvider.getInstance());
	}

	@Override
	protected void updateEntity(Person oldEntity, Person newEntity) {

	}
}
