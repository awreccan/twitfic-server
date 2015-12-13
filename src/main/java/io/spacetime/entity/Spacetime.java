package io.spacetime.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Spacetime implements Serializable {
	private static final long serialVersionUID = 9179315299231136703L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	private int latitude;
	private int longitude;
	
	@Temporal(value=TemporalType.TIMESTAMP)
	private Date date;
	
	public Spacetime() {
		this(0, 0, new Date());
	}

	public Spacetime(int latitude, int longitude, Date date){
		this.latitude = latitude;
		this.longitude = longitude;
		this.date = date;
	}
	
	@Override
	public String toString() {
		return "Spacetime [" + 
				this.latitude + ", " + 
				this.longitude + ", " + 
				this.date + "]"; 
	}

	public int getLatitude() {
		return latitude;
	}

	public void setLatitude(int latitude) {
		this.latitude = latitude;
	}

	public int getLongitude() {
		return longitude;
	}

	public void setLongitude(int longitude) {
		this.longitude = longitude;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public int getId() {
		return id;
	}
	
}
