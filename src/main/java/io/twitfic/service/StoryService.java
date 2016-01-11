package io.twitfic.service;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;

import io.twitfic.dao.StoryDao;
import io.twitfic.entity.Story;

@Stateless
@LocalBean
public class StoryService {

	@Inject
	StoryDao dao;
	
	public Story getStory(int id) {
		return dao.findStoryById(id);
	}

	public List<Story> getAllStories() {
		return dao.findAllStories();
	}

	public Story setStory(Story story) {
		return dao.saveStory(story);
	}

	public Story setStoryById(int id, Story story) {
		Story existingStory = dao.findStoryById(id);
		existingStory.setAccounts(story.getAccounts());
		return dao.saveStory(existingStory);
	}

	public boolean removeStory(Story story) {
		try {
			dao.removeStory(story);
			return true;
		}
		catch (Exception e) {
			// FIXME: specific exception? RuntimeException?
			return false;
		}
	}

	public void deleteStoryById(int id) {
		dao.removeStoryById(id);
	}

}
