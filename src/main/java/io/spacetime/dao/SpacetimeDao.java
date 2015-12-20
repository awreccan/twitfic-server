package io.spacetime.dao;

import java.util.Date;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import io.spacetime.entity.Spacetime;
import io.spacetime.entity.Spacetime_;

@Stateless
@LocalBean
public class SpacetimeDao {
	
	@PersistenceContext
	EntityManager em;
	
	public Spacetime findSpacetimeById(int id) {
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Spacetime> query = builder.createQuery(Spacetime.class);
		Root<Spacetime> root = query.from(Spacetime.class);
		query.where(builder.equal(root.get(Spacetime_.id), id));
		Spacetime spacetime = null;
		try {
			spacetime = em.createQuery(query).getSingleResult();
		} catch (NoResultException e) {}
		return spacetime;
	}

	public Spacetime saveSpacetime(int latitude, int longitude, Date date) {
		Spacetime newSpacetime = new Spacetime(latitude, longitude, date);
		em.persist(newSpacetime);
		return newSpacetime;
	}
	
	public Spacetime saveSpacetime(Spacetime spacetime) {
		return em.merge(spacetime);
	}

	public List<Spacetime> findAllSpacetimes() {
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Spacetime> query = builder.createQuery(Spacetime.class);
		query.from(Spacetime.class);
		List<Spacetime> spacetimes = em.createQuery(query).getResultList();
		return spacetimes;
	}

	public void removeSpacetime(Spacetime spacetimeJustCreated) {
		if (em.contains(spacetimeJustCreated))
			em.remove(spacetimeJustCreated);
		else
			this.removeSpacetimeById(spacetimeJustCreated.getId());
	}
	
	public void removeSpacetimeById(int id) {
		Spacetime spacetime = this.findSpacetimeById(id);
		if (spacetime != null)
			em.remove(spacetime);
	}
}
