package io.twitfic.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlTransient;

@XmlAccessorType(XmlAccessType.FIELD)
@Entity
public class Account implements Serializable {
	private static final long serialVersionUID = -3214789815075877279L;

	@XmlID @XmlAttribute
	@Id
	private String handle;
	private String name;

	@XmlTransient
	@OneToMany(mappedBy="account", fetch=FetchType.LAZY)
	private List<Tweet> tweets;
	
	@XmlTransient
	@ManyToOne
	@JoinColumn(nullable=false)
	private Story story;
	
	public Account() {}

	public Account(String handle, String name){
		initializeTweets();
		
		this.setHandle(handle);
		this.setName(name);
	}
	
	@Override
	public String toString() {
		return "Account (" + 
					this.handle + ", " +
					this.name + 
				")"; 
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Account) {
			Account account = (Account) obj;
			return 
					Objects.equals(account.getHandle(), this.getHandle()) &&
					Objects.equals(account.getName(), this.getName());
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(this.getHandle(), this.getName());
	}

	public String getHandle() {
		return handle;
	}

	public void setHandle(String handle) {
		this.handle = handle;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void initializeTweets() {
		this.tweets = new ArrayList<Tweet>();
	}

	public List<Tweet> getTweets() {
		return tweets;
	}
	
	public void setTweets(List<Tweet> tweets) {
		this.tweets = tweets;
	}

	public void addTweet(Tweet tweet) {
		// Sanitise
		if (this.tweets == null)
			initializeTweets();
		
		this.tweets.add(tweet);
	}

	public Story getStory() {
		return story;
	}

	public void setStory(Story story) {
		this.story = story;
	}
}
