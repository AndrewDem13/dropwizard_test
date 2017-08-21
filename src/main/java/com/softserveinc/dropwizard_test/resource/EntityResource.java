package com.softserveinc.dropwizard_test.resource;

import com.codahale.metrics.annotation.Timed;
import com.softserveinc.dropwizard_test.entity.Entity;
import com.softserveinc.dropwizard_test.service.BasicService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/test-entity")
@Produces(MediaType.APPLICATION_JSON)
public class EntityResource {
    private BasicService service;

    public EntityResource(BasicService service) {
        this.service = service;
    }

    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    @Timed
    public Response addTest(Entity entity) {
        Entity result = (Entity) service.createEntity(entity);
        if (result != null) {
            return Response.status(Response.Status.CREATED).entity(result).build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @Path("{id}")
    @GET
    @Timed
    public Response getByIdTest(@PathParam("id") Long id) {
        Entity result = (Entity) service.getEntity(id);
        if (result != null) {
            return Response.status(Response.Status.FOUND).entity(result).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @GET
    @Timed
    public Response getTest() {
        List<Entity> result = service.getAll();
        if (result != null && result.size() > 0) {
            return Response.status(Response.Status.FOUND).entity(result).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @Path("{id}")
    @PUT
    @Consumes({MediaType.APPLICATION_JSON})
    @Timed
    public Response updateTest(@PathParam("id") Long id, Entity entity) {
        if (getByIdTest(id).getEntity() != null) {
            Entity result = (Entity) service.updateEntity(entity);
            if (result != null) {
                return Response.status(Response.Status.OK).entity(result).build();
            } else {
                return Response.status(Response.Status.BAD_REQUEST).build();
            }
        } else {
            return addTest(entity);
        }
    }

    @Path("{id}")
    @DELETE
    @Timed
    public Response deleteTest(@PathParam("id") Long id) {
        if (service.deleteEntity(id)) {
            return Response.status(Response.Status.OK).build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

}
