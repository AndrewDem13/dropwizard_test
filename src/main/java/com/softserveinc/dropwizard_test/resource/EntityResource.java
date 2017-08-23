package com.softserveinc.dropwizard_test.resource;

import com.codahale.metrics.annotation.Timed;
import com.softserveinc.dropwizard_test.entity.Entity;
import com.softserveinc.dropwizard_test.service.CrudService;
import com.softserveinc.dropwizard_test.service.impl.EntityService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/test-entity")
@Produces(MediaType.APPLICATION_JSON)
public class EntityResource {

    private final EntityService service;

    @Inject
    public EntityResource(EntityService service) {
        this.service = service;
    }

    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    @Timed
    public Response add(Entity entity) {
        service.create(entity);
        return Response.status(Response.Status.CREATED).entity(entity).build();
    }

    @GET
    @Path("{message}")
    @Timed
    public Response getByMessage(@PathParam("message") String message) {
        Entity result = (Entity) service.get(message);
        if (result != null) {
            return Response.status(Response.Status.FOUND).entity(result).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @GET
    @Timed
    public Response get() {
        List<Entity> result = service.getAll();
        if (result != null && result.size() > 0) {
            return Response.status(Response.Status.FOUND).entity(result).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @PUT
    @Path("{message}")
    @Consumes({MediaType.APPLICATION_JSON})
    @Timed
    public Response update(@PathParam("message") String message, Entity entity) {
        Object result = service.update(message, entity);
        if (result != null) {
            return Response.status(Response.Status.OK).entity(entity).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @DELETE
    @Path("{message}")
    @Timed
    public Response delete(@PathParam("message") String message) {
        if (service.delete(message)) {
            return Response.status(Response.Status.OK).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
}
