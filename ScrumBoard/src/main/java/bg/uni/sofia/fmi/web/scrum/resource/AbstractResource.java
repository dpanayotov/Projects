package bg.uni.sofia.fmi.web.scrum.resource;

import bg.uni.sofia.fmi.web.scrum.dao.AbstractDao;
import bg.uni.sofia.fmi.web.scrum.model.JpaEntity;

public abstract class AbstractResource<Key, Entity extends JpaEntity<Key>> {

	
	protected Entity findById(Key id){
		return this.getDao().findById(id);
	}
	
	protected void create(Entity e){
		this.getDao().create(e);
	}
	
	protected abstract AbstractDao<Key, Entity> getDao();
}
