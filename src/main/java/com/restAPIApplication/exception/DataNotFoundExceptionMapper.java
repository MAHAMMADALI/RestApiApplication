package com.restAPIApplication.exception;

import com.restAPIApplication.model.ErrorMessage;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class DataNotFoundExceptionMapper implements ExceptionMapper<DataNotFoundException> {

    @Override
    public Response toResponse(DataNotFoundException e) {
        ErrorMessage errorMessage = new ErrorMessage(e.getMessage(), 404, "document");
        return Response.status(Response.Status.NOT_FOUND).entity(errorMessage).build();
    } //http://localhost:9000/messenger/webapi/messages/09
}
/* {
    "documentation": "document",
    "errorCode": 404,
    "errorMessage": "Message with id 9 not found"
} */