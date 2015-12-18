package io.spacetime.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Date;
import java.util.Random;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.spacetime.entity.Spacetime;

@RunWith(Arquillian.class)
public class SpacetimeDaoTest {

	@Inject
	SpacetimeDao dao;
	
	@PersistenceContext
	EntityManager em;
	
	private static final int NUM_MILLIS_IN_A_DAY = 1000*60*60*24;
	
	@Deployment
	public static JavaArchive createDeployment() {
		JavaArchive archive = ShrinkWrap.create(JavaArchive.class);
		
		archive.addClasses(Spacetime.class, SpacetimeDao.class);
		archive.addAsResource("test-persistence.xml", "META-INF/persistence.xml");
		// To enable CDI
		archive.addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
		
		System.out.println(archive.toString(true));
		
		return archive;
	}
	
	@Test
	public void testCreateAndDeleteSpacetime() {
		Random random = new Random();
		int latitude = random.nextInt();
		int longitude = random.nextInt();
		Date date = new Date(System.currentTimeMillis() + random.nextInt()*NUM_MILLIS_IN_A_DAY);
		
		Spacetime spacetime = new Spacetime(latitude, longitude, date);
		assertTrue(spacetime.getId() == 0); // Java primitives are zero-initialized
		
		Spacetime spacetimeJustPersisted = dao.saveSpacetime(spacetime);
		
		spacetimeJustPersisted = dao.findSpacetimeById(spacetimeJustPersisted.getId());
		
		assertEquals(latitude, spacetimeJustPersisted.getLatitude());
		assertEquals(longitude, spacetimeJustPersisted.getLongitude());
		assertEquals(date, spacetimeJustPersisted.getDate());
		
		//Removing the Spacetime just created
		dao.removeSpacetime(spacetimeJustPersisted);
	}
}
