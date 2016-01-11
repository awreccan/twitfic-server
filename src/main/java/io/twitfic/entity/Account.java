package io.twitfic.entity;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlTransient;

import com.fasterxml.jackson.annotation.JsonIgnore;

@XmlAccessorType(XmlAccessType.FIELD)
@Entity
public class Account implements Serializable {
	private static final long serialVersionUID = -3214789815075877279L;

	@Id
	private String handle;
	private String name;
	
	@OneToMany(mappedBy="account", fetch=FetchType.EAGER, cascade=CascadeType.ALL, orphanRemoval=true)
	private List<Tweet> tweets;
	
	@XmlTransient @JsonIgnore
	@ManyToOne
	@JoinColumn(nullable=false)
	private Story story;
	
	public Account() {}

	public Account(String handle, String name, List<Tweet> tweets){
		this.setHandle(handle);
		this.setName(name);
		this.setTweets(tweets);
	}
	
	@Override
	public String toString() {
		return "Account {" + 
				this.handle + ", " + 
				this.name + ", " + 
				this.tweets + "}"; 
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Account) {
			Account account = (Account) obj;
			return 
					Objects.equals(account.getHandle(), this.getHandle()) &&
					Objects.equals(account.getName(), this.getName()) &&
					Objects.equals(account.getTweets(), this.getTweets());
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(this.getHandle(), this.getName(), this.getTweets());
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

	public List<Tweet> getTweets() {
		return tweets;
	}

	public void setTweets(List<Tweet> tweets) {
		this.tweets = tweets;
		for (Tweet tweet : tweets)
			tweet.setAccount(this);
	}

	public Story getStory() {
		return story;
	}

	public void setStory(Story story) {
		this.story = story;
	}
	
}
