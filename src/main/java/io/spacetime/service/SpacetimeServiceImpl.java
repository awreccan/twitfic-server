package io.spacetime.service;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import io.spacetime.dao.SpacetimeDao;
import io.spacetime.entity.Spacetime;

@Stateless
public class SpacetimeServiceImpl implements SpacetimeServiceIntf {

	@EJB
	SpacetimeDao dao;
	
	@Override
	public Spacetime getSpacetime(int id) {
		return dao.findSpacetimeById(id);
	}

	@Override
	public Spacetime setSpacetime(Spacetime spacetime) {
		return dao.updateSpacetime(spacetime);
	}

	@Override
	public boolean removeSpacetime(Spacetime spacetime) {
		try {
			dao.removeSpacetime(spacetime);
			return true;
		}
		catch (Exception e) { //FIXME
			return false;
		}
	}

	@Override
	public List<Spacetime> getAllSpacetimes() {
		return dao.findAllSpacetimes();
	}

}
