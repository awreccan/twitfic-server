package io.spacetime.service;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;

import io.spacetime.entity.Spacetime;

@Stateless
public class SpacetimeServiceImpl implements SpacetimeServiceIntf {

	@Override
	public Spacetime getSpacetime(int id) {
		return new Spacetime(); //FIXME
	}

	@Override
	public Spacetime setSpacetime(Spacetime spacetime) {
		return null; // FIXME
	}

	@Override
	public boolean removeSpacetime() {
		return true; //FIXME
	}

	@Override
	public List<Spacetime> getAllSpacetimes() {
		List<Spacetime> spacetimes = new ArrayList<Spacetime>();
		spacetimes.add(new Spacetime());
		return spacetimes;
	}

}
