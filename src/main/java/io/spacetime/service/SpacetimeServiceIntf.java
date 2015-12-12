package io.spacetime.service;

import java.util.List;

import javax.ejb.Remote;

import io.spacetime.entity.Spacetime;

@Remote
public interface SpacetimeServiceIntf {
	public Spacetime getSpacetime(int id);
	public Spacetime setSpacetime(Spacetime spacetime);
	public boolean removeSpacetime();
	
	public List<Spacetime> getAllSpacetimes();
}
