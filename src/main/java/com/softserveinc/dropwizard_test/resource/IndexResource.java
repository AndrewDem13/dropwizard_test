package com.softserveinc.dropwizard_test.resource;

import com.codahale.metrics.annotation.Timed;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/")
@Produces(value = MediaType.APPLICATION_JSON)
public class IndexResource {

    @GET
    @Timed
    public Response index() {
        return Response.ok().entity("Hello World").build();
    }
}
