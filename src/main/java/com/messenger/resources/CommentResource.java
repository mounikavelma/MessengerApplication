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
import javax.ws.rs.core.MediaType;

import com.messenger.model.Comment;
import com.messenger.service.CommentServices;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CommentResource {
	
	private CommentServices commentservices= new CommentServices();
	
	@GET
	public List<Comment> getAllComments(@PathParam("messageId") long messageId)
	{
		return commentservices.getAllComments(messageId);
		
	}
	
	@POST
	public Comment addComment(@PathParam("messageId") long messageId, Comment comment)
	{
		return commentservices.addComment(messageId, comment);
	}

	@PUT
	@Path("/{commentId}")
	public Comment updateComment(@PathParam("messageId") long messageId,@PathParam("commentId") long commentId, Comment comment)
	{
		comment.setId(commentId);
		return commentservices.updateComment(messageId, comment);
	}
	
	@DELETE
	@Path("/{commentId}")
	public void deleteComment(@PathParam("messageId") long messageId, @PathParam("commentId") long commentId)
	{
		commentservices.removeComment(messageId, commentId);
	}
	
	@GET
	@Path("/{commentId}")
	public Comment getComment(@PathParam("messageId") long messageId, @PathParam("commentId") long commentId)
	{
		return commentservices.getComment(messageId, commentId);
	}
	

}
