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
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
public class Story implements Serializable {
	private static final long serialVersionUID = 9179315299231136703L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	@OneToMany(mappedBy="story", fetch=FetchType.EAGER, cascade=CascadeType.ALL, orphanRemoval=true)
	private List<Account> accounts;
	
	public Story() {}

	public Story(List<Account> accounts){
		this.setAccounts(accounts);
	}
	
	@Override
	public String toString() {
		return "Story: " + this.accounts; 
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Story) {
			Story story = (Story) obj;
			return Objects.equals(story.getAccounts(), this.getAccounts());
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(this.getAccounts());
	}

	public List<Account> getAccounts() {
		return accounts;
	}

	public void setAccounts(List<Account> accounts) {
		for (Account account : accounts)
			account.setStory(this);
		this.accounts = accounts;
	}

	public int getId() {
		return id;
	}
	
}
