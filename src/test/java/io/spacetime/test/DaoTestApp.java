package io.spacetime.test;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import io.spacetime.entity.Spacetime;

public class DaoTestApp {

	private static final int NUM_MILLIS_IN_A_DAY = 1000*60*60*24;
	private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("SpacetimeDB");
	private static EntityManager em = emf.createEntityManager();
	
	public static void main(String[] args) {
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		
		showCurrentState();
		
		Spacetime newSpacetime = new Spacetime(3, 3, new Date(System.currentTimeMillis() + 3*NUM_MILLIS_IN_A_DAY));
		em.persist(newSpacetime);
		int idOfCreatedSpacetime = newSpacetime.getId(); //TODO - check if this returns the generated id of the persisted entity
		
		showCurrentState();
		
		Spacetime spacetimeJustCreated = (Spacetime) em.createQuery(
				"SELECT spacetime FROM Spacetime spacetime WHERE spacetime.id = :id")
				.setParameter("id", idOfCreatedSpacetime)
				.getSingleResult();
		System.out.println("Removing the Spacetime just created: " + spacetimeJustCreated);
		em.remove(spacetimeJustCreated);
		showCurrentState();
		
		Spacetime preexistingSpacetime = (Spacetime) em.createQuery(
				"SELECT spacetime FROM Spacetime spacetime WHERE spacetime.id = :id")
				.setParameter("id", 51)
				.getSingleResult();
		Date newDate = new Date(NUM_MILLIS_IN_A_DAY + preexistingSpacetime.getDate().getTime());
		System.out.println("Advancing time of " + preexistingSpacetime + " by one day.");
		preexistingSpacetime.setDate(newDate);
		
		showCurrentState();
		
		tx.commit();
		em.close();
		
	}

	private static void showCurrentState() {
		List<Spacetime> spacetimes = em.createQuery(
				"SELECT spacetime FROM Spacetime spacetime")
				.getResultList();
		System.out.println();
		System.out.println("Current state:");
		for (Spacetime s : spacetimes)
			System.out.println(s);
		System.out.println();
	}

}
