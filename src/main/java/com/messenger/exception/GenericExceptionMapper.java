package com.messenger.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.messenger.model.errorMessage;

//@Provider
public class GenericExceptionMapper implements ExceptionMapper<Throwable> {

	@Override
	public Response toResponse(Throwable ex) {
		// TODO Auto-generated method stub
		errorMessage errormessage= new errorMessage(ex.getMessage(), 500, "http://www.google.com");
		return Response.status(Status.INTERNAL_SERVER_ERROR)
				.entity(errormessage)		
				.build();
	}

}
