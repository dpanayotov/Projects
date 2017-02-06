package bg.uni.sofia.fmi.web.scrum.resource;

import java.net.URI;
import java.util.List;

import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import bg.uni.sofia.fmi.web.scrum.dao.AbstractDao;
import bg.uni.sofia.fmi.web.scrum.model.JpaEntity;

public abstract class AbstractResource<Key, Entity extends JpaEntity<Key>> {

	protected List<Entity> findAll() {
		return this.getDao().findAll();
	}

	protected Entity findById(Key id) {
		return this.getDao().findById(id);
	}

	protected URI create(Entity e, UriInfo uriInfo) {
		this.getDao().create(e);
		String uri = uriInfo.getAbsolutePath().toString();
		return UriBuilder.fromPath(uri + "/" + e.getKey()).build();
	}
	
	protected Entity update(Entity e){
		return this.getDao().update(e.getKey(), e);
	}

	protected abstract AbstractDao<Key, Entity> getDao();
}
