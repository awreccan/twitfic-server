package io.twitfic.resource;

import static com.jayway.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.net.URI;
import java.net.URL;
import java.util.List;

import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriBuilder;

import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.jayway.restassured.response.Response;

import io.twitfic.base.BaseTwitficTest;
import io.twitfic.entity.Account;
import io.twitfic.entity.Story;

@RunWith(Arquillian.class)
public class StoryResourceTest extends BaseTwitficTest {

	@ArquillianResource
	private URL deploymentUrl;
	
	private Story clientStory, serverStory;
//	private static int serverStoryId;

/*	@Test
	public void testMoat() throws InterruptedException {
		int count = 0;
		Random rand = new Random();
		int[] list = new int[] {1,10,100,11,110,111};

		while(true){
			for (int i = 0; i < list.length; i++){
//			clientStory = new Story(generateRandomAccountsWithTweets(2, 5));
			Tweet tweet = new Tweet(new Date(NUM_MILLIS_IN_A_DAY + rand.nextInt(1000)*1000 + list[i]), "c");
			Account account = new Account("h", "n", Arrays.asList(new Tweet[] {tweet}));
			clientStory = new Story(Arrays.asList(new Account[] {account}));
			
			Response response = given()
				.body(clientStory)
				.contentType("application/json")
			.when()
				.post(buildUri("twitfic", "stories"));

			serverStory = response.as(Story.class);

			System.out.println(count++);
			System.out.println(clientStory.equals(serverStory));
			System.out.print("");
			TimeUnit.MILLISECONDS.sleep(100);
			}
//			assertEquals(clientStory, serverStory);
		}
	}*/
	@Before
	public void testCreate() {
		clientStory = new Story(accounts);
		assertEquals(0, clientStory.getId()); // Java primitives are zero-initialized
		
		Response response = given()
			.body(clientStory)
			.contentType("application/json")
		.expect()
			.contentType("application/json")
			.statusCode(Status.OK.getStatusCode())
		.when()
			.post(buildUri("twitfic", "stories"));

		serverStory = response.as(Story.class);
//		serverStoryId = serverStory.getId();

		assertEquals(clientStory, serverStory);
		assertNotEquals(clientStory.getId(), serverStory.getId());
	}

	@Test
	public void testGet() {
		Response response = given()
		.expect()
			.contentType("application/json")
			.statusCode(Status.OK.getStatusCode())
		.when()
			.get(buildUri("twitfic", "stories", Integer.toString(serverStory.getId())));
		
		Story fetchedServerStory = response.as(Story.class);
		
		assertEquals(clientStory, fetchedServerStory);
		assertEquals(serverStory.getId(), fetchedServerStory.getId());
	}

	@Test
	public void testUpdate() {
		List<Account> tmpAccounts = generateRandomAccountsWithTweets(accounts.size(), accounts.size());
		clientStory.setAccounts(tmpAccounts);
		
		int serverStoryId = serverStory.getId();
		
		Response response = given()
			.body(clientStory)
			.contentType("application/json")
		.expect()
			.contentType("application/json")
			.statusCode(Status.OK.getStatusCode())
		.when()
			.post(buildUri("twitfic", "stories", Integer.toString(serverStoryId)));

		serverStory = response.as(Story.class);
		
		assertEquals(clientStory, serverStory);
		assertEquals(serverStoryId, serverStory.getId());
	}

	@After
	public void testDeleteAndVerify() {
		given()
		.expect()
			.statusCode(Status.NO_CONTENT.getStatusCode())
		.when()
			.delete(buildUri("twitfic", "stories", Integer.toString(serverStory.getId())));

		Response response = given()
		.expect()
			.contentType("application/json")
			.statusCode(Status.OK.getStatusCode())
		.when()
			.get(buildUri("twitfic", "stories"));
		
		Story[] fetchedStories = response.as(Story[].class);
		assertEquals(0, fetchedStories.length);
	}

	URI buildUri(String... paths) {
		UriBuilder builder = UriBuilder.fromUri(deploymentUrl.toString());
		for (String path : paths) {
			builder.path(path);
		}
		return builder.build();
	}


}
