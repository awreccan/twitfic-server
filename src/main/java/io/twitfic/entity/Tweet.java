package io.twitfic.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;

@Entity
public class Tweet implements Serializable {
	private static final long serialVersionUID = -6614777061913809713L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	@ManyToOne
	@JoinColumn
	private Account author;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date time;
	
	@Size(min = 1, max = 140)
	private String content;
	
	@ManyToOne
	@JoinColumn
	private Story story;
	
	public Tweet() {}

	public Tweet(Account author, Date time, String content) {
		this.author = author;
		this.time = time;
		this.content = content;
		
		// Accounts keep track of their Tweets to allow for cascading while deleting an Account
		author.getTweets().add(this);
	}
	
	@Override
	public String toString() {
		return "Tweet [" + 
				this.author + ", " +
				this.time + ", " +
				this.content + "]"; 
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Tweet) {
			Tweet stObj = (Tweet) obj;
			return 
					Objects.equals(stObj.getAuthor(), this.getAuthor()) &&
					Objects.equals(stObj.getTime(), this.getTime()) &&
					Objects.equals(stObj.getContent(), this.getContent());
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(this.getAuthor(), this.getTime(), this.getContent());
	}

	public Account getAuthor() {
		return author;
	}

	public void setAuthor(Account author) {
		this.author = author;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getId() {
		return id;
	}

}
