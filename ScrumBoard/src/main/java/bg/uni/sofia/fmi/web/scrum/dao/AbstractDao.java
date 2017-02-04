package bg.uni.sofia.fmi.web.scrum.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import bg.uni.sofia.fmi.web.scrum.model.JpaEntity;
import bg.uni.sofia.fmi.web.scrum.persistence.EntityManagerProvider;

public abstract class AbstractDao<Key, Entity extends JpaEntity<Key>> {

	private Class<Entity> clazz;
	private EntityManagerProvider emProvider;

	protected AbstractDao(Class<Entity> clazz, EntityManagerProvider emProvider) {
		this.clazz = clazz;
		this.emProvider = emProvider;
	}

	public void create(Entity e) {
		EntityManager em = emProvider.get();
		em.getTransaction().begin();
		em.persist(e);
		em.getTransaction().commit();
	}

	public Entity findById(Key id) {
		EntityManager em = emProvider.get();
		return em.find(clazz, id);
	}

	public List<Entity> findAll() {
		EntityManager em = emProvider.get();
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Entity> query = cb.createQuery(clazz);
		query.select(query.from(clazz));
		TypedQuery<Entity> tq = em.createQuery(query);
		return tq.getResultList();
	}

	public long count() {
		EntityManager em = emProvider.get();
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Long> cq = cb.createQuery(Long.class);
		cq.select(cb.count(cq.from(clazz)));
		return em.createQuery(cq).getSingleResult();
	}

	public void delete(Entity entity) {
		EntityManager em = emProvider.get();
		em.getTransaction().begin();
		em.remove(entity);
		em.getTransaction().commit();
	}

	public Entity update(Key key, Entity newEntity) {
		EntityManager em = emProvider.get();
		em.getTransaction().begin();
		Entity fromDb = findById(key);
		updateEntity(fromDb, newEntity);
		Entity merged = em.merge(fromDb);
		em.getTransaction().commit();
		return merged;
	}

	protected abstract void updateEntity(Entity oldEntity, Entity newEntity);
}
