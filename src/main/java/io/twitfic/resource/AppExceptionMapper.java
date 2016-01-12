package io.twitfic.resource;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * To log uncaught JAXRS exceptions
 */
@Provider
public class AppExceptionMapper implements ExceptionMapper<Exception> {

	@Override
	public Response toResponse(Exception exception) {
    	exception.printStackTrace();
    	return Response.serverError().entity(exception.getMessage()).build();
	} 
}
