package io.spacetime.service;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;

import io.spacetime.dao.SpacetimeDao;
import io.spacetime.entity.Spacetime;

@Stateless
@LocalBean
public class SpacetimeService {

	@Inject
	SpacetimeDao dao;
	
	public Spacetime getSpacetime(int id) {
		return dao.findSpacetimeById(id);
	}

	public Spacetime setSpacetime(Spacetime spacetime) {
		return dao.saveSpacetime(spacetime);
	}

	public boolean removeSpacetime(Spacetime spacetime) {
		try {
			dao.removeSpacetime(spacetime);
			return true;
		}
		catch (Exception e) {
			// FIXME: specific exception? RuntimeException?
			return false;
		}
	}

	public List<Spacetime> getAllSpacetimes() {
		return dao.findAllSpacetimes();
	}

}
