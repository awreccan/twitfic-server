package io.spacetime.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import io.spacetime.entity.Spacetime;

@Stateless
@LocalBean
public class SpacetimeDao {
	public Spacetime readSpacetime(int id) {//FIXME
		return new Spacetime();
	}

	public Spacetime updateSpacetime(Spacetime spacetime) {// FIXME
		return null;
	}

	public boolean removeSpacetime() {//FIXME
		return true;
	}

	public List<Spacetime> findAllSpacetimes() {
		List<Spacetime> spacetimes = new ArrayList<Spacetime>();
		spacetimes.add(new Spacetime());
		spacetimes.add(new Spacetime(0, 0,
				new Date(System.currentTimeMillis() - 1000*60*60*24*7)));
		return spacetimes;
	}
}
