package io.spacetime.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Random;

import io.spacetime.util.Location;

public class Spacetime implements Serializable {
	private static final Location DEFAULT_LOCATION = new Location(0, 0);
	
	private int id;
	private Location location;
	private Date date;
	
	private Random idGenerator;
	
	public Spacetime() {
		this(DEFAULT_LOCATION, new Date());
	}

	public Spacetime(Location location, Date date){
		this.idGenerator = new Random();
		this.id = idGenerator.nextInt();
		this.location = location;
		this.date = date;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
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

	public void setId(int id) {
		this.id = id;
	}
	
	@Override
	public String toString() {
		return "Spacetime[" + this.location + ", " + this.date + "]"; 
	}
	
}
