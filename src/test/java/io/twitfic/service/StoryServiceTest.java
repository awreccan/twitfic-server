package io.twitfic.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.List;

import javax.inject.Inject;

import org.jboss.arquillian.junit.Arquillian;
import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import io.twitfic.base.BaseTwitficTest;
import io.twitfic.entity.Account;
import io.twitfic.entity.Story;

@RunWith(Arquillian.class)
public class StoryServiceTest extends BaseTwitficTest {
	
	@Inject
	StoryService service;
	
	private Story clientStory, serverStory;
	
/*	@Test
	public void testMoat() {
		int count = 0;
		while(true){
			
			clientStory = new Story(generateRandomAccountsWithTweets(2, 5));
			serverStory = service.setStory(clientStory);

			System.out.println(count++);
			System.out.println(serverStory.equals(clientStory));
			System.out.print("");
			
			assertEquals(clientStory, serverStory);
		}
	}*/
	
	@Before
	public void testCreate() {
		clientStory = new Story(accounts);
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
		List<Account> tmpAccounts = generateRandomAccountsWithTweets(accounts.size(), accounts.size());
		serverStory.setAccounts(tmpAccounts);
		serverStory = service.setStory(serverStory);
		clientStory.setAccounts(tmpAccounts);
		assertEquals(clientStory, serverStory);
	}
	
	@After
	public void testDelete() {
		service.removeStory(serverStory);
		serverStory = service.getStory(serverStory.getId());
		assertNull(serverStory);
	}
}
