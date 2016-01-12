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
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.XmlTransient;

import com.fasterxml.jackson.annotation.JsonFormat;

@XmlAccessorType(XmlAccessType.FIELD)
@Entity
public class Tweet implements Serializable {
	private static final long serialVersionUID = -6614777061913809713L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(pattern="yyy-MM-dd'T'HH:mm:ss")
	private Date time;
	
	@Size(min = 1, max = 140)
	private String content;
	
	@XmlIDREF
	@ManyToOne
	@JoinColumn(nullable=false)
	private Account account;

	@XmlTransient
	@ManyToOne
	@JoinColumn(nullable=false)
	private Story story;
	
	public Tweet() {}

	public Tweet(Date time, String content, Account account) {
		this.setTime(time);
		this.setContent(content);
		this.setAccount(account);
	}
	
	@Override
	public String toString() {
		return "Tweet {" + 
					this.time + ", " +
					this.content + ", " +
					this.account + 
				"}"; 
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Tweet) {
			Tweet tweet = (Tweet) obj;
			return
					// Time comparison will be done to only second precision because there are
					// parsing problems introduced when the milliseconds field length < 3 digits
					Objects.equals(tweet.getTime().getTime()/1000, this.getTime().getTime()/1000) &&
					Objects.equals(tweet.getContent(), this.getContent()) &&
					Objects.equals(tweet.getAccount(), this.getAccount());
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(this.getTime(), this.getContent(), this.getAccount());
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		// Sanitise
		account.addTweet(this);
		
		this.account = account;
	}

	public Story getStory() {
		return story;
	}

	public void setStory(Story story) {
		this.story = story;
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
