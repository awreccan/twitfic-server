package io.twitfic.entity;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

@Entity
public class Story implements Serializable {
	private static final long serialVersionUID = 9179315299231136703L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	@OneToMany(mappedBy="story", fetch=FetchType.EAGER, cascade=CascadeType.ALL)
	private List<Account> accounts;
	
	@OneToMany(mappedBy="story", fetch=FetchType.EAGER, cascade=CascadeType.ALL)
	@OrderBy("time DESC")
	private List<Tweet> tweets;
	
	public Story() {}

	public Story(List<Account> accounts, List<Tweet> tweets){
		this.accounts = accounts;
		this.tweets = tweets;
	}
	
	@Override
	public String toString() {
		return "Story [" + 
				this.accounts + ", " + 
				this.tweets + "]"; 
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Story) {
			Story stObj = (Story) obj;
			return 
					Objects.equals(stObj.getAccounts(), this.getAccounts()) &&
					Objects.equals(stObj.getTweets(), this.getTweets());
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(this.getAccounts(), this.getTweets());
	}

	public List<Account> getAccounts() {
		return accounts;
	}

	public void setAccounts(List<Account> accounts) {
		this.accounts = accounts;
	}

	public List<Tweet> getTweets() {
		return tweets;
	}

	public void setTweets(List<Tweet> tweets) {
		this.tweets = tweets;
	}

	public int getId() {
		return id;
	}
	
}
