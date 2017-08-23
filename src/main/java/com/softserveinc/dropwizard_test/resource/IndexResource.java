package com.softserveinc.dropwizard_test.resource;

import com.codahale.metrics.annotation.Timed;
import com.softserveinc.dropwizard_test.service.BasicService;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/")
@Produces(value = MediaType.APPLICATION_JSON)
public class IndexResource {

    @Inject
    BasicService service;

    @GET
    @Timed
    public Response index() {
        return Response.ok().entity(service.getEntity("test")).build();
    }
}
