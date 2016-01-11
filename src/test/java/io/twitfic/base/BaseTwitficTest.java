package io.twitfic.base;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.junit.Before;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.restassured.internal.mapping.Jackson2Mapper;

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
	protected static List<Account> accounts;

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
		// For JPA
		archive.addAsResource("test-persistence.xml", "META-INF/persistence.xml");
		// To enable CDI
		archive.addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");

		System.out.println(archive.toString(true));

		return archive;
	}

	@Before
	public void setup() {
		// Generate 2-5 random tweets encapsulated within minimum 2 accounts
		accounts = generateRandomAccountsWithTweets(2, 5);
	}
	
	protected List<Account> generateRandomAccountsWithTweets(int minAccounts, int maxTweets) {
		Random random = new Random();
		int numTweets = random.nextInt(maxTweets - 1) + minAccounts;

		List<Tweet> tweets = generateRandomTweets(random, numTweets);
		List<Integer> tweetsPerAccountList = generateTweetsPerAccountList(random, numTweets, minAccounts);

		List<Account> accounts = new ArrayList<Account>();
		for (int i = 0; i < tweetsPerAccountList.size(); i++) {
			String handle = UUID.randomUUID().toString().replace("-", "");
			String name = "John Doe " + handle.substring(0, 8);

			List<Tweet> tweetsOfCurrAccount = new ArrayList<Tweet>();
			for (int j = 0; j < tweetsPerAccountList.get(i); j++) {
				tweetsOfCurrAccount.add(tweets.get(j));
			}
			accounts.add(new Account(handle, name, tweetsOfCurrAccount));
		}
		
		return accounts;
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

	private /*static*/ List<Tweet> generateRandomTweets(Random random, int numTweets) {
		List<Tweet> tmpTweets = new ArrayList<Tweet>();
		for (int i = 0; i < numTweets; i++) {
			long millis = System.currentTimeMillis() + random.nextInt() * NUM_MILLIS_IN_A_DAY;
			millis -= millis%1000; // round down to a second
			tmpTweets.add(new Tweet(new Date(millis),
					random.nextInt(100) + " is the answer to everything."));
		}
		return tmpTweets;
	}
	
/*	public static void main(String[] args) throws InterruptedException{
		Random r = new Random();
		String comparison;
		List<Integer> old = new ArrayList<>(), cur;
		old.add(r.nextInt());
		old.add(r.nextInt(100));
		
		int count = 0;
		while (true) {
			System.out.println(count++);
			generateRandomTweets(r, 5);
			TimeUnit.MILLISECONDS.sleep(500);*/
			/*cur = new ArrayList<>();
			cur.add(r.nextInt());
			cur.add(r.nextInt(100));
			comparison = (cur.get(0).equals(old.get(0))) + " " + (cur.get(1).equals(old.get(1)));
			System.out.print(cur.get(0) + " ");
			System.out.print(cur.get(1) + " ");
			System.out.print(old.get(0) + " ");
			System.out.print(old.get(1) + " ");
			System.out.println(comparison);*/
	/*		System.out.print("");
			
//			old = cur;
		}
	}*/
	
/*	public static class DaDate {
		@JsonFormat(pattern="yyy-MM-dd'T'HH:mm:ss.SSSXXX")
		public Date t;
	}
	
	public static void main(String[] args) throws ParseException, JsonParseException, JsonMappingException, JsonProcessingException, IOException {
		int count = 0;
		while (true) {
			ObjectMapper mapper = new ObjectMapper();
			long millis = System.currentTimeMillis();
			
			// Create Date
			DaDate dadate = new DaDate();
			dadate.t = new Date(millis);
			// Serialize, post, deserialize at server
			DaDate processedDate = mapper.readValue(mapper.writeValueAsString(dadate), DaDate.class);
			// Persist in server
			Timestamp ts = new Timestamp(processedDate.t.getTime());
			DaDate daRetDate = new DaDate();
			daRetDate.t = (Date) ts;
			// Send back to client (serialize, post, deserialize)
			DaDate retDate = mapper.readValue(mapper.writeValueAsString(daRetDate), DaDate.class);
			
//			SimpleDateFormat sdf = new SimpleDateFormat("yyy-MM-dd'T'HH:mm:ss.SSSXXX");
//			Date processedDate = sdf.parse(sdf.format(date));
			
			System.out.println(count++);
			System.out.print(retDate.t.getTime());
			System.out.print(" ");
			System.out.print(retDate.t.toInstant().getNano()/1000000);
			System.out.print(" ");
			System.out.print(dadate.t.toInstant().getNano()/1000000);
			System.out.print(" ");
			System.out.print(dadate.t.getTime());
			if (!retDate.t.equals(dadate.t))
				System.out.println(" notEqual");
			System.out.println();
		}
	}*/
}
