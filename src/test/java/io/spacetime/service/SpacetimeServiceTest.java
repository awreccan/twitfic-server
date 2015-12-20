package io.spacetime.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import javax.inject.Inject;

import org.jboss.arquillian.junit.Arquillian;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.spacetime.base.BaseSpacetimeTest;
import io.spacetime.entity.Spacetime;
import io.spacetime.service.SpacetimeService;

@RunWith(Arquillian.class)
public class SpacetimeServiceTest extends BaseSpacetimeTest {
	
	@Inject
	SpacetimeService service;
	
	@Test
	public void testCreateReadDelete() {
		// Create
		Spacetime spacetime = new Spacetime(latitude, longitude, date);
		assertTrue(spacetime.getId() == 0); // Java primitives are zero-initialized
		Spacetime spacetimeJustCreated = service.setSpacetime(spacetime);
		assertEquals(spacetime, spacetimeJustCreated);
		
		// Read
		spacetimeJustCreated = service.getSpacetime(spacetimeJustCreated.getId());
		assertEquals(spacetime, spacetimeJustCreated);
		
		// Delete
		service.removeSpacetime(spacetimeJustCreated);
		spacetimeJustCreated = service.getSpacetime(spacetimeJustCreated.getId());
		assertNull(spacetimeJustCreated);
	}
	
	@Test
	public void testCreateUpdateDelete() {
		// Create
		Spacetime spacetime = new Spacetime(latitude, longitude, date);
		assertTrue(spacetime.getId() == 0); // Java primitives are zero-initialized
		Spacetime spacetimeJustCreated = service.setSpacetime(spacetime);
		assertEquals(spacetime, spacetimeJustCreated);
		
		// Update
		spacetimeJustCreated.setLatitude(1 + spacetimeJustCreated.getLatitude());
		spacetimeJustCreated = service.setSpacetime(spacetimeJustCreated);
		spacetime.setLatitude(1 + spacetime.getLatitude());
		assertEquals(spacetime, spacetimeJustCreated);
		
		// Delete
		service.removeSpacetime(spacetimeJustCreated);
		spacetimeJustCreated = service.getSpacetime(spacetimeJustCreated.getId());
		assertNull(spacetimeJustCreated);
	}
}
