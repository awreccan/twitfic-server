package io.spacetime.util;

import java.io.Serializable;

public class Location implements Serializable {
	private int latitude;
	private int longitude;
	//TODO private Address address;
	
	public Location(int latitude, int longitude) {
		this.latitude = latitude;
		this.longitude = longitude;
	}
	
	@Override
	public String toString() {
		return "Location {" + latitude + ", " + longitude + "}";
	}

}
