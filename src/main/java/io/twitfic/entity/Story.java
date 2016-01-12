package io.twitfic.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
@Entity
public class Story implements Serializable {
	private static final long serialVersionUID = 9179315299231136703L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	@OneToMany(mappedBy="story", fetch=FetchType.EAGER, cascade=CascadeType.ALL, orphanRemoval=true)
	private List<Tweet> tweets;
	
	@OneToMany(mappedBy="story", fetch=FetchType.EAGER, cascade=CascadeType.ALL, orphanRemoval=true)
	private Set<Account> accounts;
	
	public Story() {}

	public Story(List<Tweet> tweets){
		this.tweets = new ArrayList<Tweet>();
		this.accounts = new LinkedHashSet<Account>();
		
		this.addTweets(tweets);
	}
	
	@Override
	public String toString() {
		return "Story: " + this.tweets;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Story) {
			Story story = (Story) obj;
			return Objects.equals(story.getAccounts(), this.getAccounts()) &&
					Objects.equals(story.getTweets(), this.getTweets());
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(this.getAccounts(), this.getTweets());
	}

	public Set<Account> getAccounts() {
		return accounts;
	}

	public void addAccounts(Set<Account> accounts) {
		for (Account account : accounts)
			this.addAccount(account);
	}
	
	public void addAccount(Account account) {
		// Sanitise
		account.setStory(this);
		if (account.getTweets() == null)
			account.initializeTweets();
		
		this.accounts.add(account);
	}

	public List<Tweet> getTweets() {
		return tweets;
	}
	
	public void setTweets(List<Tweet> tweets) {
		// Sanitise: Remove all sub-entities except id
		this.accounts.clear();
		this.tweets.clear();
		
		this.addTweets(tweets);
	}

	public void addTweets(List<Tweet> tweets) {
		for (Tweet tweet: tweets)
			this.addTweet(tweet);
	}
	
	public void addTweet(Tweet tweet) {
		// Sanitise
		tweet.setStory(this);
		this.addAccount(tweet.getAccount());
		
		this.tweets.add(tweet);
	}

	public int getId() {
		return id;
	}
}
