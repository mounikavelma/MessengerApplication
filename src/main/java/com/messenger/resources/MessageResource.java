package com.messenger.resources;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

import com.messenger.model.Message;
import com.messenger.service.MessageService;



@Path("/messages")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class MessageResource {

	MessageService messageService = new MessageService();
	
	@GET
	public List<Message> getMessages(@QueryParam("year") int year,
									 @QueryParam("start") int start,
									 @QueryParam("size") int size) {
		if (year > 0) {
			return messageService.getAllMessagesForYear(year);
		}
		if (start >= 0 && size > 0) {
			return messageService.getAllMessagesPaginated(start, size);
		}
		return messageService.getAllMessages();
	}

	@POST
	public Message addMessage(Message message) {
		return messageService.addMessage(message);
	}
	
	@PUT
	@Path("/{messageId}")
	public Message updateMessage(@PathParam("messageId") long id, Message message) {
		message.setId(id);
		return messageService.updateMessage(message);
	}
	
	@DELETE
	@Path("/{messageId}")
	public void deleteMessage(@PathParam("messageId") long id) {
		messageService.removeMessage(id);
	}
	
	
	@GET
	@Path("/{messageId}")
	public Message getMessage(@PathParam("messageId") long id, @Context UriInfo uriInfo) {
		Message message= messageService.getMessage(id);
		message.addLinks(getUriForSelf(uriInfo, message), "self");
		message.addLinks(getUriForProfiles(uriInfo, message), "profile");
		message.addLinks(getUriForComments(uriInfo, message), "comments");
		
		return message;
	}

	private String getUriForComments(UriInfo uriInfo, Message message) {
		// TODO Auto-generated method stub
		String uri=uriInfo.getBaseUriBuilder()
				.path(MessageResource.class)
				.path(MessageResource.class, "getCommentResource")
				.path(CommentResource.class)
				.resolveTemplate("messageId", message.getId())
				.build()
				.toString();
		
		return uri;
	}

	private String getUriForProfiles(UriInfo uriInfo, Message message) {
		// TODO Auto-generated method stub
		String uri=uriInfo.getBaseUriBuilder()
				.path(ProfileResource.class)
				.path(message.getAuthor())
				.build()
				.toString();
		return uri;
	
	}

	private String getUriForSelf(UriInfo uriInfo, Message message) {
		String uri=uriInfo.getBaseUriBuilder()
				.path(MessageResource.class)
				.path(Long.toString(message.getId()))
				.build()
				.toString();
		return uri;
	}
	
	@Path("/{messageId}/comments")
	public CommentResource getCommentResource()
	{
		return new CommentResource();
	}
	
}
