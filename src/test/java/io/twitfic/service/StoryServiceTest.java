package io.twitfic.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import javax.inject.Inject;

import org.jboss.arquillian.junit.Arquillian;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.twitfic.base.BaseTwitficTest;
import io.twitfic.entity.Story;
import io.twitfic.service.StoryService;

@RunWith(Arquillian.class)
public class StoryServiceTest extends BaseTwitficTest {
	
	@Inject
	StoryService service;
	
	@Test
	public void testCreateReadDelete() {
		// Create
		Story story = new Story(latitude, longitude, date);
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
		Story story = new Story(latitude, longitude, date);
		assertTrue(story.getId() == 0); // Java primitives are zero-initialized
		Story storyJustCreated = service.setStory(story);
		assertEquals(story, storyJustCreated);
		
		// Update
		storyJustCreated.setLatitude(1 + storyJustCreated.getLatitude());
		storyJustCreated = service.setStory(storyJustCreated);
		story.setLatitude(1 + story.getLatitude());
		assertEquals(story, storyJustCreated);
		
		// Delete
		service.removeStory(storyJustCreated);
		storyJustCreated = service.getStory(storyJustCreated.getId());
		assertNull(storyJustCreated);
	}
}
