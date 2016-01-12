package io.twitfic.resource;

import static com.jayway.restassured.RestAssured.given;
import static com.jayway.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.jayway.restassured.response.Response;

import io.twitfic.base.BaseTwitficTest;
import io.twitfic.entity.Story;
import io.twitfic.entity.Tweet;

@RunWith(Arquillian.class)
public class StoryResourceTest extends BaseTwitficTest {

	@ArquillianResource
	private URL deploymentUrl;
	
	private Story clientStory, serverStory;

	@Before
	public void testCreate() throws JsonProcessingException {
		clientStory = new Story(tweets);
		assertEquals(0, clientStory.getId()); // Java primitives are zero-initialized
		
		Response response = given()
			.body(clientStory)
			.contentType("application/json")
			.log().body()
		.expect()
			.contentType("application/json")
			.statusCode(Status.OK.getStatusCode())
			.log().status().log().body()
		.when()
			.post(buildUri("twitfic", "stories"));

		response.then()
			.body(matchesJsonSchemaInClasspath("story-schema.json"));
		
		serverStory = response.as(Story.class);

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
		List<Tweet> newTweets = generateRandomTweetsWithAccounts(tweets.size(), tweets.size());
		clientStory.setTweets(newTweets);
		serverStory.setTweets(newTweets);
		
		int serverStoryId = serverStory.getId();
		
		Response response = given()
			.body(serverStory)
			.contentType("application/json")
		.expect()
			.contentType("application/json")
			.statusCode(Status.OK.getStatusCode())
		.when()
			.post(buildUri("twitfic", "stories"));

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
