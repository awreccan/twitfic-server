package io.twitfic.resource;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import io.twitfic.entity.Story;
import io.twitfic.service.StoryService;

@Path("stories")
@Consumes("application/json")
@Produces("application/json")
public class StoryResource {

	@Inject
	StoryService service;
	
	@POST
	public Story saveStory(Story story) {
		return service.setStory(story);
	}
	
	@GET
	public List<Story> getAllStories() {
		return service.getAllStories();
	}
	
	@GET
	@Path("{id}")
	public Story getStoryById(@PathParam("id") int id) {
		return service.getStory(id);
	}
	
	@DELETE
	@Path("{id}")
	public void deleteStoryById(@PathParam("id") int id) {
		service.deleteStoryById(id);
	}
}
