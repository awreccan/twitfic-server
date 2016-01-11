package io.twitfic.dao;

import static com.jayway.restassured.RestAssured.given;
import static org.junit.Assert.*;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import org.jboss.arquillian.junit.Arquillian;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.jayway.restassured.response.Response;

import io.twitfic.base.BaseTwitficTest;
import io.twitfic.entity.Account;
import io.twitfic.entity.Story;

@RunWith(Arquillian.class)
public class StoryDaoTest extends BaseTwitficTest {

	@Inject
	StoryDao dao;
	
	/*@Test
	public void testMoat() throws InterruptedException {
		int count = 0;
		while(true){
			System.out.println(count++);

			// Assert empty db
			assertTrue(dao.findAllStories().isEmpty());
			assertTrue(dao.findAllAccounts().isEmpty());
			assertTrue(dao.findAllTweets().isEmpty());
			
			// Create
			List<Account> accounts = generateRandomAccountsWithTweets(2, 5);
			Story storyJustPersisted = dao.saveStory(new Story(accounts));
			Story story = new Story(accounts);
			System.out.print(story.equals(storyJustPersisted) + " "); /////////////////////////////////
			System.out.print("");
			
			// Read
			storyJustPersisted = dao.findStoryById(storyJustPersisted.getId());
			List<Story> allStories = dao.findAllStories();
			System.out.print(allStories.get(0).equals(storyJustPersisted) + " "); ///////////
			System.out.print(story.equals(storyJustPersisted) + " "); /////////////////////////////////
			System.out.print("");
			
			// Delete
			dao.removeStoryById(storyJustPersisted.getId());
			storyJustPersisted = dao.findStoryById(storyJustPersisted.getId());
			assertNull(storyJustPersisted);
			
			// Assert empty db
			assertTrue(dao.findAllStories().isEmpty());
			assertTrue(dao.findAllAccounts().isEmpty());
			assertTrue(dao.findAllTweets().isEmpty());
	
			System.out.println();
			TimeUnit.MILLISECONDS.sleep(500);
			
//			assertEquals(clientStory, serverStory);
		}
	}*/
	
	@Before @After
	public void testEmptyDatabase() {
		assertTrue(dao.findAllStories().isEmpty());
		assertTrue(dao.findAllAccounts().isEmpty());
		assertTrue(dao.findAllTweets().isEmpty());
	}
	
	@Test
	public void testCreateReadDeleteStoryByMembers() {
		// Create
		Story storyJustPersisted = dao.saveStory(accounts);
		assertNotEquals(0, storyJustPersisted.getId()); // should have a newly generated Id
		Story story = new Story(accounts);
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
		Story story = new Story(accounts);
		assertEquals(0, story.getId()); // Java primitives are zero-initialized
		Story storyJustPersisted = dao.saveStory(story);
		assertNotEquals(0, storyJustPersisted.getId()); // should have a newly generated Id
		assertEquals(story, storyJustPersisted);
		
		// Update
		List<Account> tmpAccounts = generateRandomAccountsWithTweets(accounts.size(), accounts.size()*2);
		storyJustPersisted.setAccounts(tmpAccounts);
		storyJustPersisted = dao.saveStory(storyJustPersisted);
		story.setAccounts(tmpAccounts);
		assertEquals(story, storyJustPersisted);
		
		// Delete
		dao.removeStory(storyJustPersisted);
		storyJustPersisted = dao.findStoryById(storyJustPersisted.getId());
		assertNull(storyJustPersisted);
	}
}
