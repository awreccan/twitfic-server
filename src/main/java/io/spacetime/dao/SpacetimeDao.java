package io.spacetime.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import io.spacetime.entity.Spacetime;

@Stateless
@LocalBean
public class SpacetimeDao {
	@PersistenceContext
	EntityManager em;
	
	public Spacetime findSpacetimeById(int id) {//FIXME
		return (Spacetime) em.createQuery(
				"SELECT spacetime FROM Spacetime spacetime WHERE spacetime.id = :id")
				.setParameter("id", id)
				.getSingleResult();
	}

	public Spacetime updateSpacetime(Spacetime spacetime) {// FIXME
		return null;
	}

	public Spacetime saveSpacetime(int latitude, int longitude, Date date) {//FIXME
		Spacetime newSpacetime = new Spacetime(latitude, longitude, date);
		return this.saveSpacetime(newSpacetime);
	}
	
	public Spacetime saveSpacetime(Spacetime spacetime) {
		em.persist(spacetime);
		return spacetime;
	}

	public List<Spacetime> findAllSpacetimes() {
		List<Spacetime> spacetimes = new ArrayList<Spacetime>();
		spacetimes.add(new Spacetime());
		spacetimes.add(new Spacetime(0, 0,
				new Date(System.currentTimeMillis() - 1000*60*60*24*7)));
		return spacetimes;
	}

	public void removeSpacetime(Spacetime spacetimeJustCreated) {
		if (em.contains(spacetimeJustCreated))
			em.remove(spacetimeJustCreated);
		else
			this.removeSpacetimeById(spacetimeJustCreated.getId());
	}
	
	public void removeSpacetimeById(int id) {
		em.remove(this.findSpacetimeById(id));
	}
}
