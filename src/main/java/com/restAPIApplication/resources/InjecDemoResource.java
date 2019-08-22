package com.restAPIApplication.resources;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

@Path("/injectdemo")
@Consumes(MediaType.TEXT_PLAIN)
@Produces(MediaType.TEXT_PLAIN)
public class InjecDemoResource {
    @GET
    @Path("annotations")
    //If you are sure for what parameter you are looking for the use below annoations.
    public String getParamsUsingAnnotations(@MatrixParam("param") String matrixParam,
                                            @HeaderParam("authSessionID") String header,
                                            @CookieParam("Cookie_1") String cookie){
        return "Matrix param:  " + matrixParam + "authSessionID  "+ header + "Cookie   " +cookie;
    }//http://localhost:9000/messenger/webapi/injectdemo/annotations;param=value

    @GET
    @Path("context")
    //If you are not sure for what parameter you are looking for the use below annoations to get all info of the resource.
    public String getParamsUsingContext(@Context UriInfo uriInfo, @Context HttpHeaders headers){
        String path = uriInfo.getAbsolutePath().toString();
        String cookies = headers.getCookies().toString();
        return"Path:  "+ path+" Cookies: "+cookies;
    }//http://localhost:9000/messenger/webapi/injectdemo/context
}//output: Path:  http://localhost:9000/messenger/webapi/injectdemo/context Cookies: {JSESSIONID=$Version=0;JSESSIONID=4821FBF27F43E81A893E678E03559DB3}
