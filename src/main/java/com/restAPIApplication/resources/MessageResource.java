package com.restAPIApplication.resources;

import com.restAPIApplication.model.Message;
import com.restAPIApplication.resources.beans.MessageFilterBean;
import com.restAPIApplication.service.MessageService;

import javax.annotation.PostConstruct;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@Path("/messages")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class MessageResource {
    MessageService service = new MessageService();
    @GET
    //public List<Message> getMessages(){ return service.getAllMessages();    }
    public List<Message> getMessages(@BeanParam MessageFilterBean filterBean){
        if(filterBean.getYear() > 0) return service.getAllMessagesForYear(filterBean.getYear());
        if(filterBean.getStart() > 0 && filterBean.getSize() > 0) return service.getAllMessagesPaginated(filterBean.getStart(), filterBean.getSize());
        return service.getAllMessages();
    } //http://localhost:9000/messenger/webapi/messages?year=2018
    //http://localhost:9000/messenger/webapi/messages?start=0&size=2

    @GET
    @Path("/{messageId}")
    @Produces(MediaType.APPLICATION_JSON)
    /*public Message getMessage(@PathParam("messageId") long messageId){ return service.getMessage(messageId); } */
    public Message getMessage(@PathParam("messageId") long messageId, @Context UriInfo uriInfo){
        Message message = service.getMessage(messageId);
        message.addLink(getUriForSelf(uriInfo, message), "self");
        message.addLink(getUriForProfile(uriInfo, message), "Profile");
        message.addLink(getUriForComments(uriInfo, message), "Comments");
        return message;
    }/* URI:  http://localhost:9000/messenger/webapi/messages/1/
    Output: {
    "author": "Ali",
    "created": "2019-08-22T18:09:49.976",
    "id": 1,
    "links": [
        {
            "rel": "self",
            "url": "http://localhost:9000/messenger/webapi/messages/1"
        },
        {
            "rel": "Profile",
            "url": "http://localhost:9000/messenger/webapi/profiles/Ali"
        },
        {
            "rel": "Comments",
            "url": "http://localhost:9000/messenger/webapi/messages/1/comments/"
        }
    ],
    "message": "Hello World!"
}*/
    private String getUriForComments(UriInfo uriInfo, Message message) {
        URI uri = uriInfo.getBaseUriBuilder()
                .path(MessageResource.class)
                .path(MessageResource.class, "getCommentResource")
                .path(CommentResource.class)
                .resolveTemplate("messageId", message.getId())
                .build();
        return uri.toString();
    }/*Output  {
            "rel": "Comments",
            "url": "http://localhost:9000/messenger/webapi/messages/1/comments/"
        }  */

    private String getUriForProfile(UriInfo uriInfo, Message message) {
        URI uri = uriInfo.getBaseUriBuilder()
                .path(ProfileResource.class)
                .path(message.getAuthor())
                .build();
        return uri.toString();
    }/*Output    {
            "rel": "Profile",
            "url": "http://localhost:9000/messenger/webapi/profiles/Ali"
        }*/

    private String getUriForSelf(@Context UriInfo uriInfo, Message message) {
        return uriInfo.getBaseUriBuilder()
                .path(MessageResource.class)
                .path(Long.toString((message.getId())))
                .build().toString();
    }/* Output: {
            "rel": "self",
            "url": "http://localhost:9000/messenger/webapi/messages/1"
        }*/

    @POST
    /*public Message addMessage(Message message){ return service.addMessage(message); }*/
    //Sending Status Codes and Location under Headers using Responce Builder class
    public Response addMessaage(Message message, @Context UriInfo uriInfo){
        Message newMessage = service.addMessage(message);
        String newID = String.valueOf(newMessage.getId());
        URI uri = uriInfo.getAbsolutePathBuilder().path(newID).build();
        return Response.created(uri).entity(newMessage).build();
    }

    @PUT
    @Path("/{messageId}")
    public Message updateMessage(@PathParam("messageId") long id, Message message){
        message.setId(id);
        return service.updateMessage(message);
    }

    @DELETE
    @Path("/{messageId}")
    public void deleteMessage(@PathParam("messageId") long id){
       service.removeMessage(id);
    }

    //Calling Sub-Resource like messages/1/comments/1
    @Path("/{messageId}/comments")
    public CommenResource getCommentResource(){ return new CommenResource();  }
}
/*http://localhost:9000/messenger/webapi/messages/1  --> will get details of 1 id
http://localhost:9000/messenger/webapi/messages/2  --> will get details of 2 id
http://localhost:9000/messenger/webapi/messages/<AnyOtherNum>  --> will get status code:  204 No Content*/
