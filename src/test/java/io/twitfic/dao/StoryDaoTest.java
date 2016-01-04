package io.twitfic.dao;

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
import io.twitfic.dao.StoryDao;
import io.twitfic.entity.Story;
import io.twitfic.entity.Tweet;

@RunWith(Arquillian.class)
public class StoryDaoTest extends BaseTwitficTest {

	@Inject
	StoryDao dao;
	
	@Test
	public void testCreateReadDeleteStoryByMembers() {
		// Create
		Story storyJustPersisted = dao.saveStory(accounts, tweets);
		Story story = new Story(accounts, tweets);
		assertEquals(story, storyJustPersisted);
		
		// Read
		storyJustPersisted = dao.findStoryById(storyJustPersisted.getId());
		assertEquals(dao.findAllStories().get(0), storyJustPersisted);
		assertEquals(story, storyJustPersisted);
		
		// Delete
		dao.removeStoryById(storyJustPersisted.getId());
		storyJustPersisted = dao.findStoryById(storyJustPersisted.getId());
		assertNull(storyJustPersisted);
	}
	
	@Test
	public void testCreateUpdateDeleteStory() {
		// Create
		Story story = new Story(accounts, tweets);
		assertTrue(story.getId() == 0); // Java primitives are zero-initialized
		Story storyJustPersisted = dao.saveStory(story);
		assertEquals(story, storyJustPersisted);
		
		// Update
		List<Tweet> tmpTweets = generateRandomTweets(new Random(), accounts.size(), accounts.size());
		storyJustPersisted.setTweets(tmpTweets);
		storyJustPersisted = dao.saveStory(storyJustPersisted);
		story.setTweets(tmpTweets);
		assertEquals(story, storyJustPersisted);
		
		// Delete
		dao.removeStory(storyJustPersisted);
		storyJustPersisted = dao.findStoryById(storyJustPersisted.getId());
		assertNull(storyJustPersisted);
	}
}
