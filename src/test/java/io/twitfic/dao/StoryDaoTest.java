package io.twitfic.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import javax.inject.Inject;

import org.jboss.arquillian.junit.Arquillian;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.twitfic.base.BaseTwitficTest;
import io.twitfic.dao.StoryDao;
import io.twitfic.entity.Story;

@RunWith(Arquillian.class)
public class StoryDaoTest extends BaseTwitficTest {

	@Inject
	StoryDao dao;
	
	@Test
	public void testCreateReadDeleteStory() {
		// Create
		Story story = new Story(latitude, longitude, date);
		assertTrue(story.getId() == 0); // Java primitives are zero-initialized
		Story storyJustPersisted = dao.saveStory(story);
		assertEquals(story, storyJustPersisted);
		
		// Read
		storyJustPersisted = dao.findStoryById(storyJustPersisted.getId());
		assertEquals(story, storyJustPersisted);
		
		// Delete
		dao.removeStory(storyJustPersisted);
		storyJustPersisted = dao.findStoryById(storyJustPersisted.getId());
		assertNull(storyJustPersisted);
	}
	
	@Test
	public void testCreateUpdateDeleteStory() {
		// Create
		Story story = new Story(latitude, longitude, date);
		assertTrue(story.getId() == 0); // Java primitives are zero-initialized
		Story storyJustPersisted = dao.saveStory(story);
		assertEquals(story, storyJustPersisted);
		
		// Update
		storyJustPersisted.setLatitude(1 + storyJustPersisted.getLatitude());
		storyJustPersisted = dao.saveStory(storyJustPersisted);
		story.setLatitude(1 + story.getLatitude());
		assertEquals(story, storyJustPersisted);
		
		// Delete
		dao.removeStory(storyJustPersisted);
		storyJustPersisted = dao.findStoryById(storyJustPersisted.getId());
		assertNull(storyJustPersisted);
	}
}
