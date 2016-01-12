package io.twitfic.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

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
public class StoryDaoTest extends BaseTwitficTest {

	@Inject
	StoryDao dao;
	
	
	@Before
	public void testEmptyDatabaseBeforeTest() {
		testEmptyDatabase();
	}
	
	@After
	public void testEmptyDatabaseAfterTest() {
		testEmptyDatabase();
	}
	
	private void testEmptyDatabase() {
		assertTrue(dao.findAllStories().isEmpty());
		assertTrue(dao.findAllAccounts().isEmpty());
		assertTrue(dao.findAllTweets().isEmpty());
	}
	
	@Test
	public void testCreateReadDeleteStoryByMembers() {
		// Create
		Story storyJustPersisted = dao.saveStory(tweets);
		assertNotEquals(0, storyJustPersisted.getId()); // should have a newly generated Id
		Story story = new Story(tweets);
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
		Story story = new Story(tweets);
		assertEquals(0, story.getId()); // Java primitives are zero-initialized
		Story storyJustPersisted = dao.saveStory(story);
		assertNotEquals(0, storyJustPersisted.getId()); // should have a newly generated Id
		assertEquals(story, storyJustPersisted);
		
		// Update
		List<Tweet> newTweets = generateRandomTweetsWithAccounts(tweets.size(), tweets.size()*2);
		storyJustPersisted.setTweets(newTweets);
		storyJustPersisted = dao.saveStory(storyJustPersisted);
		story.setTweets(newTweets);
		assertEquals(story, storyJustPersisted);
		
		// Delete
		dao.removeStory(storyJustPersisted);
		storyJustPersisted = dao.findStoryById(storyJustPersisted.getId());
		assertNull(storyJustPersisted);
	}
}
