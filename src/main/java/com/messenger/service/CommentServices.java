package com.messenger.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.messenger.database.DatabaseClass;
import com.messenger.model.Comment;
import com.messenger.model.Message;
import com.messenger.model.errorMessage;


public class CommentServices {
	
	private Map<Long, Message> messages= DatabaseClass.getMessages();
	
	
	public List<Comment> getAllComments(long messageId)
	{
		Map<Long, Comment> comments= messages.get(messageId).getComments();
		return new ArrayList<Comment>(comments.values());
	}
	
	public Comment getComment(long messageId, long commentId)
	{
		errorMessage errormessage= new errorMessage("not found", 500, "http://www.google.com");
		Response response= Response.status(Status.INTERNAL_SERVER_ERROR)
				.entity(errormessage)		
				.build();
		
		Message message = messages.get(messageId);
		if(message == null){
			throw new WebApplicationException(response);
		}
		Map<Long, Comment> comments=messages.get(messageId).getComments();
		Comment comment= comments.get(commentId);
		if(comment == null){
			throw new WebApplicationException(response);
		}
		
		return comment;
	}
	
	public Comment addComment(long messageId, Comment comment)
	{
		Map<Long, Comment> comments=messages.get(messageId).getComments();
		comment.setId(comments.size()+1);
		comments.put(comment.getId(), comment);
		return comment;
	}
	
	public Comment updateComment(long messageId, Comment comment)
	{
		
		Map<Long, Comment> comments=messages.get(messageId).getComments();
		if(comment.getId()<=0)
		{
			return null;
		}
		
		comments.put(comment.getId(), comment);
		return comment;
	}
	
	public Comment removeComment(long messageId, long commentId)
	{
		Map<Long, Comment> comments=messages.get(messageId).getComments();
		return comments.remove(commentId);
	}

}
