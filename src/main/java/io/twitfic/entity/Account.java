package io.twitfic.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Account implements Serializable {
	private static final long serialVersionUID = -3214789815075877279L;

	@Id
	private String handle;
	private String name;
	
	@ManyToOne
	@JoinColumn
	private Story story;
	
	@OneToMany(mappedBy="author", fetch=FetchType.LAZY, cascade=CascadeType.REMOVE)
	private List<Tweet> tweets;
	
	public Account() {}

	public Account(String handle, String name){
		this.handle = handle;
		this.name = name;
		tweets = new ArrayList<Tweet>();
	}
	
	@Override
	public String toString() {
		return "Account [" + 
				this.handle + ", " + 
				this.name + "]"; 
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Account) {
			Account stObj = (Account) obj;
			return 
					Objects.equals(stObj.getHandle(), this.getHandle()) &&
					Objects.equals(stObj.getName(), this.getName());
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

	public List<Tweet> getTweets() {
		// TODO: replace with defensive copy and add tweets modification methods
		return tweets;
	}

	public void setTweets(List<Tweet> tweets) {
		this.tweets = tweets;
	}
	
}
