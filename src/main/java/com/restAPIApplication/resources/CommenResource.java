package com.restAPIApplication.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

@Path("/")
public class CommenResource {

    @GET
    public String test(){ return "new sub resource ";}

    @GET
    @Path("/{commentId}")
    public String test2(@PathParam("messageId") long messageID, @PathParam("commentId") long commentID){ return "Method to return MessageID: "+messageID+" comment ID "+commentID;}
}//http://localhost:9000/messenger/webapi/messages/12/comments/2
