package io.twitfic.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.List;

import javax.inject.Inject;

import org.jboss.arquillian.junit.Arquillian;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.twitfic.base.BaseTwitficTest;
import io.twitfic.entity.Story;
import io.twitfic.entity.Tweet;

@RunWith(Arquillian.class)
public class StoryServiceTest extends BaseTwitficTest {
	
	@Inject
	StoryService service;
	
	private Story clientStory, serverStory;
	
	@Before
	public void testCreate() {
		clientStory = new Story(tweets);
		assertEquals(0, clientStory.getId()); // Java primitives are zero-initialized
		serverStory = service.setStory(clientStory);
		assertEquals(clientStory, serverStory);
	}
	
	@Test
	public void testGet() {
		serverStory = service.getStory(serverStory.getId());
		assertEquals(clientStory, serverStory);
	}
	
	@Test
	public void testUpdate() {
		List<Tweet> newTweets = generateRandomTweetsWithAccounts(tweets.size(), tweets.size());
		serverStory.setTweets(newTweets);
		serverStory = service.setStory(serverStory);
		clientStory.setTweets(newTweets);
		assertEquals(clientStory, serverStory);
	}
	
	@After
	public void testDelete() {
		service.removeStory(serverStory);
		serverStory = service.getStory(serverStory.getId());
		assertNull(serverStory);
	}
}
