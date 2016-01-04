package io.twitfic.dao;

import java.util.Date;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import io.twitfic.entity.Story_;
import io.twitfic.entity.Story;

@Stateless
@LocalBean
public class StoryDao {
	
	@PersistenceContext
	EntityManager em;
	
	public Story findStoryById(int id) {
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Story> query = builder.createQuery(Story.class);
		Root<Story> root = query.from(Story.class);
		query.where(builder.equal(root.get(Story_.id), id));
		Story story = null;
		try {
			story = em.createQuery(query).getSingleResult();
		} catch (NoResultException e) {}
		return story;
	}

	public Story saveStory(int latitude, int longitude, Date date) {
		Story newStory = new Story(latitude, longitude, date);
		em.persist(newStory);
		return newStory;
	}
	
	public Story saveStory(Story story) {
		return em.merge(story);
	}

	public List<Story> findAllStories() {
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Story> query = builder.createQuery(Story.class);
		query.from(Story.class);
		List<Story> stories = em.createQuery(query).getResultList();
		return stories;
	}

	public void removeStory(Story storyJustCreated) {
		if (em.contains(storyJustCreated))
			em.remove(storyJustCreated);
		else
			this.removeStoryById(storyJustCreated.getId());
	}
	
	public void removeStoryById(int id) {
		Story story = this.findStoryById(id);
		if (story != null)
			em.remove(story);
	}
}
