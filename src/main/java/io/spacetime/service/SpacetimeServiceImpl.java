package io.spacetime.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import io.spacetime.dao.SpacetimeDao;
import io.spacetime.entity.Spacetime;
import io.spacetime.util.Location;

@Stateless
public class SpacetimeServiceImpl implements SpacetimeServiceIntf {

	@EJB
	SpacetimeDao dao;
	
	@Override
	public Spacetime getSpacetime(int id) {
		return dao.readSpacetime(id);
	}

	@Override
	public Spacetime setSpacetime(Spacetime spacetime) {
		return dao.updateSpacetime(spacetime);
	}

	@Override
	public boolean removeSpacetime() {
		return dao.removeSpacetime();
	}

	@Override
	public List<Spacetime> getAllSpacetimes() {
		return dao.findAllSpacetimes();
	}

}
