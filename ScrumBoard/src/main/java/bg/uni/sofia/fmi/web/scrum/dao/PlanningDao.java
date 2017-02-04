package bg.uni.sofia.fmi.web.scrum.dao;

import bg.uni.sofia.fmi.web.scrum.model.Planning;
import bg.uni.sofia.fmi.web.scrum.persistence.EntityManagerProvider;

public class PlanningDao extends AbstractDao<Long, Planning> {

	public PlanningDao() {
		super(Planning.class, EntityManagerProvider.getInstance());
	}

	@Override
	protected void updateEntity(Planning oldEntity, Planning newEntity) {

	}
}
