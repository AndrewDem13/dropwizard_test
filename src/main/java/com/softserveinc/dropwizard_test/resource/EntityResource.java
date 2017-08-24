package com.softserveinc.dropwizard_test.resource;

import com.codahale.metrics.annotation.Timed;
import com.softserveinc.dropwizard_test.entity.Entity;
import com.softserveinc.dropwizard_test.service.impl.EntityService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Random;

@Path("/entity")
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
        // TODO normal ID validation
        if (entity.getId() == 0) {
            entity.setId(new Random().nextInt(1000));
        }
        service.create(entity);
        return Response.status(Response.Status.CREATED).entity(entity).build();
    }

    @GET
    @Path("{id}")
    @Timed
    public Response getById(@PathParam("id") int id) {
        Entity result = service.get(id);
        if (result != null) {
            return Response.status(Response.Status.OK).entity(result).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @GET
    @Timed
    public Response get() {
        List<Entity> result = service.getAll();
        if (result != null && result.size() > 0) {
            return Response.status(Response.Status.OK).entity(result).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_JSON})
    @Timed
    public Response update(@PathParam("id") int id, Entity entity) {
        Object result = service.update(id, entity);
        if (result != null) {
            return Response.status(Response.Status.OK).entity(entity).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @DELETE
    @Path("{id}")
    @Timed
    public Response delete(@PathParam("id") int id) {
        if (service.delete(id)) {
            return Response.status(Response.Status.OK).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
}
