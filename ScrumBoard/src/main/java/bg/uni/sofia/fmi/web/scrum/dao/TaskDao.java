package bg.uni.sofia.fmi.web.scrum.dao;

import bg.uni.sofia.fmi.web.scrum.model.Task;
import bg.uni.sofia.fmi.web.scrum.persistence.EntityManagerProvider;

public class TaskDao extends AbstractDao<Long, Task> {

	public TaskDao() {
		super(Task.class, EntityManagerProvider.getInstance());
	}

	@Override
	protected void updateEntity(Task oldEntity, Task newEntity) {

	}
}
