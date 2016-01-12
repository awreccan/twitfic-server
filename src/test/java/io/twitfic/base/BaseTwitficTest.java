package io.twitfic.base;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.junit.Before;

import io.twitfic.dao.StoryDao;
import io.twitfic.entity.Account;
import io.twitfic.entity.Story;
import io.twitfic.entity.Tweet;
import io.twitfic.resource.AppExceptionMapper;
import io.twitfic.resource.CorsFilter;
import io.twitfic.resource.StoryResource;
import io.twitfic.resource.TwitFicApp;
import io.twitfic.service.StoryService;

public class BaseTwitficTest {
	protected static final int NUM_MILLIS_IN_A_DAY = 1000 * 60 * 60 * 24;
	protected static List<Tweet> tweets;

	@Deployment
	public static WebArchive createDeployment() {
		WebArchive archive = ShrinkWrap.create(WebArchive.class);

		archive.addClasses(
				// JPA
				Story.class, Account.class, Tweet.class,
				// EJB
				StoryDao.class, StoryService.class,
				// JAX-RS
				StoryResource.class, TwitFicApp.class, CorsFilter.class, AppExceptionMapper.class);

		// For JAX-RS testing
		archive.addAsLibraries(Maven.resolver().loadPomFromFile("pom.xml")
				.resolve("com.jayway.restassured:rest-assured").withTransitivity().asFile());
		archive.addAsLibraries(Maven.resolver().loadPomFromFile("pom.xml")
				.resolve("com.jayway.restassured:json-schema-validator").withTransitivity().asFile());
		archive.addAsResource("story-schema.json", "META-INF/story-schema.json");
		// For JPA
		archive.addAsResource("test-persistence.xml", "META-INF/persistence.xml");
		// To enable CDI
		archive.addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");

		System.out.println(archive.toString(true));

		return archive;
	}

	@Before
	public void setup() {
		// Generate 2-5 random tweets with minimum 2 accounts
		tweets = generateRandomTweetsWithAccounts(2, 5);
	}
	
	protected List<Tweet> generateRandomTweetsWithAccounts(int minAccounts, int maxTweets) {
		Random random = new Random();
		int numTweets = random.nextInt(maxTweets - 1) + minAccounts;

		List<Integer> tweetsPerAccountList = generateTweetsPerAccountList(random, numTweets, minAccounts);
		List<Tweet> tweets = generateRandomTweets(random, tweetsPerAccountList);

		return tweets;
	}

	private List<Integer> generateTweetsPerAccountList(Random random, int numTweets, int minAccounts) {
		List<Integer> tweetsPerAccountList = new ArrayList<Integer>();
		int taken = 0, numTweetsForCurrentAccount;
		int minAccountsLeft = numTweets == minAccounts ? minAccounts - 1 : minAccounts;
		while (taken < numTweets) {
			numTweetsForCurrentAccount = random.nextInt(numTweets - taken - minAccountsLeft) + 1;
			tweetsPerAccountList.add(numTweetsForCurrentAccount);
			if (minAccountsLeft > 0)
				minAccountsLeft--;
			taken += numTweetsForCurrentAccount;
		}
		return tweetsPerAccountList;
	}

	private /*static*/ List<Tweet> generateRandomTweets(Random random, List<Integer> tweetsPerAccountList) {
		List<Tweet> tweets = new ArrayList<Tweet>();
		int numAccounts = tweetsPerAccountList.size();
		for (int i = 0; i < numAccounts; i++){
			String handle = UUID.randomUUID().toString().replace("-", "");
			String name = "John Doe " + handle.substring(0, 8);
			Account account = new Account(handle, name);
			for (int j = 0; j < tweetsPerAccountList.get(i); j++) {
				long millis = System.currentTimeMillis() + random.nextInt() * NUM_MILLIS_IN_A_DAY;
				millis -= millis%1000; // round down to a second
				tweets.add(new Tweet(
						new Date(millis),
						random.nextInt(100) + " is the answer to everything.",
						account));
			}
		}
		return tweets;
	}
}
