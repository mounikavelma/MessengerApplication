package com.messenger.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.messenger.model.errorMessage;

@Provider
public class DataNotFoundExceptionMapper implements ExceptionMapper<DataNotFoundException> {

	@Override
	public Response toResponse(DataNotFoundException ex) {
		// TODO Auto-generated method stub
		
		errorMessage errormessage= new errorMessage(ex.getMessage(), 404, "http://www.google.com");
		return Response.status(Status.NOT_FOUND)
				.entity(errormessage)		
				.build();
	}

}
