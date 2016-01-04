package io.twitfic.base;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Before;

import io.twitfic.dao.StoryDao;
import io.twitfic.entity.Story;
import io.twitfic.entity.Tweet;
import io.twitfic.entity.Account;
import io.twitfic.service.StoryService;

public class BaseTwitficTest {
	protected static final int NUM_MILLIS_IN_A_DAY = 1000*60*60*24;
	protected static List<Tweet> tweets;
	protected static List<Account> accounts;
	
	@Deployment
	public static JavaArchive createDeployment() {
		JavaArchive archive = ShrinkWrap.create(JavaArchive.class);
		
		archive.addPackage(Story.class.getPackage());
		archive.addClasses(StoryDao.class,
				StoryService.class);
		archive.addAsResource("test-persistence.xml", "META-INF/persistence.xml");
		// To enable CDI
		archive.addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
		
		System.out.println(archive.toString(true));
		
		return archive;
	}
	
	@Before
	public void setup() {
		/* Generate random tweets and accounts */
		Random random = new Random();
		int numUsers = random.nextInt(3) + 2;
		int numTweets = random.nextInt(numUsers) + numUsers;
		
		accounts = new ArrayList<Account>();
		for (int i = 0; i < numUsers; i++) {
			String uuid = UUID.randomUUID().toString().replace("-", "");
			accounts.add(new Account(uuid, "John Doe " + uuid.substring(0, 8)));
		}
		
		tweets = generateRandomTweets(random, numUsers, numTweets);
	}

	protected List<Tweet> generateRandomTweets(Random random, int numUsers, int numTweets) {
		List<Tweet> tmpTweets = new ArrayList<Tweet>();
		for (int i = 0; i < numTweets; i++) {
			tmpTweets.add(new Tweet(accounts.get(random.nextInt(numUsers)),
					new Date(System.currentTimeMillis() + random.nextInt()*NUM_MILLIS_IN_A_DAY),
					random.nextInt(100) + " is the answer to everything."));
		}
		return tmpTweets;
	}
}
