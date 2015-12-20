package io.spacetime.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import javax.inject.Inject;

import org.jboss.arquillian.junit.Arquillian;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.spacetime.base.BaseSpacetimeTest;
import io.spacetime.dao.SpacetimeDao;
import io.spacetime.entity.Spacetime;

@RunWith(Arquillian.class)
public class SpacetimeDaoTest extends BaseSpacetimeTest {

	@Inject
	SpacetimeDao dao;
	
	@Test
	public void testCreateReadDeleteSpacetime() {
		// Create
		Spacetime spacetime = new Spacetime(latitude, longitude, date);
		assertTrue(spacetime.getId() == 0); // Java primitives are zero-initialized
		Spacetime spacetimeJustPersisted = dao.saveSpacetime(spacetime);
		assertEquals(spacetime, spacetimeJustPersisted);
		
		// Read
		spacetimeJustPersisted = dao.findSpacetimeById(spacetimeJustPersisted.getId());
		assertEquals(spacetime, spacetimeJustPersisted);
		
		// Delete
		dao.removeSpacetime(spacetimeJustPersisted);
		spacetimeJustPersisted = dao.findSpacetimeById(spacetimeJustPersisted.getId());
		assertNull(spacetimeJustPersisted);
	}
	
	@Test
	public void testCreateUpdateDeleteSpacetime() {
		// Create
		Spacetime spacetime = new Spacetime(latitude, longitude, date);
		assertTrue(spacetime.getId() == 0); // Java primitives are zero-initialized
		Spacetime spacetimeJustPersisted = dao.saveSpacetime(spacetime);
		assertEquals(spacetime, spacetimeJustPersisted);
		
		// Update
		spacetimeJustPersisted.setLatitude(1 + spacetimeJustPersisted.getLatitude());
		spacetimeJustPersisted = dao.saveSpacetime(spacetimeJustPersisted);
		spacetime.setLatitude(1 + spacetime.getLatitude());
		assertEquals(spacetime, spacetimeJustPersisted);
		
		// Delete
		dao.removeSpacetime(spacetimeJustPersisted);
		spacetimeJustPersisted = dao.findSpacetimeById(spacetimeJustPersisted.getId());
		assertNull(spacetimeJustPersisted);
	}
}
