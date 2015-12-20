package io.spacetime.base;

import java.util.Date;
import java.util.Random;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Before;

import io.spacetime.dao.SpacetimeDao;
import io.spacetime.entity.Spacetime;
import io.spacetime.service.SpacetimeService;

public class BaseSpacetimeTest {
	protected static final int NUM_MILLIS_IN_A_DAY = 1000*60*60*24;
	protected static int latitude;
	protected static int longitude;
	protected static Date date;
	
	@Deployment
	public static JavaArchive createDeployment() {
		JavaArchive archive = ShrinkWrap.create(JavaArchive.class);
		
		archive.addClasses(Spacetime.class, SpacetimeDao.class,
				SpacetimeService.class);
		archive.addAsResource("test-persistence.xml", "META-INF/persistence.xml");
		// To enable CDI
		archive.addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
		
		System.out.println(archive.toString(true));
		
		return archive;
	}
	
	@Before
	public void setup() {
		Random random = new Random();
		latitude = random.nextInt();
		longitude = random.nextInt();
		date = new Date(System.currentTimeMillis() + random.nextInt()*NUM_MILLIS_IN_A_DAY);
	}
}
