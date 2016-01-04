package io.twitfic.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Random;

import javax.inject.Inject;

import org.jboss.arquillian.junit.Arquillian;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.twitfic.base.BaseTwitficTest;
import io.twitfic.entity.Story;
import io.twitfic.entity.Tweet;
import io.twitfic.service.StoryService;

@RunWith(Arquillian.class)
public class StoryServiceTest extends BaseTwitficTest {
	
	@Inject
	StoryService service;
	
	@Test
	public void testCreateReadDelete() {
		// Create
		Story story = new Story(accounts, tweets);
		assertTrue(story.getId() == 0); // Java primitives are zero-initialized
		Story storyJustCreated = service.setStory(story);
		assertEquals(story, storyJustCreated);
		
		// Read
		storyJustCreated = service.getStory(storyJustCreated.getId());
		assertEquals(story, storyJustCreated);
		
		// Delete
		service.removeStory(storyJustCreated);
		storyJustCreated = service.getStory(storyJustCreated.getId());
		assertNull(storyJustCreated);
	}
	
	@Test
	public void testCreateUpdateDelete() {
		// Create
		Story story = new Story(accounts, tweets);
		assertTrue(story.getId() == 0); // Java primitives are zero-initialized
		Story storyJustCreated = service.setStory(story);
		assertEquals(story, storyJustCreated);
		
		// Update
		List<Tweet> tmpTweets = generateRandomTweets(new Random(), accounts.size(), accounts.size());
		storyJustCreated.setTweets(tmpTweets);
		storyJustCreated = service.setStory(storyJustCreated);
		story.setTweets(tmpTweets);
		assertEquals(story, storyJustCreated);
		
		// Delete
		service.removeStory(storyJustCreated);
		storyJustCreated = service.getStory(storyJustCreated.getId());
		assertNull(storyJustCreated);
	}
}
