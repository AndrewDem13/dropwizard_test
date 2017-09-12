package com.softserveinc.dropwizard_test.resource;

import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.Timer;
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
    private Timer postTimer;
    private Timer getTimer;
    private Timer putTimer;
    private Timer deleteTimer;

    @Inject
    public EntityResource(EntityService service, MetricRegistry customMetricRegistry) {
        this.service = service;
        postTimer = customMetricRegistry.timer("POST Timer");
        getTimer = customMetricRegistry.timer("GET Timer");
        putTimer = customMetricRegistry.timer("PUT Timer");
        deleteTimer = customMetricRegistry.timer("DELETE Timer");
    }

    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    public Response add(Entity entity) {
        Timer.Context context = postTimer.time();
        if (entity.getId() == 0) {
            entity.setId(new Random().nextInt(1000));
        }
        service.create(entity);
        context.stop();
        return Response.status(Response.Status.CREATED).entity(entity).build();
    }

    @GET
    @Path("{id}")
    public Response getById(@PathParam("id") int id) {
        Timer.Context context = getTimer.time();
        Entity result = service.get(id);
        context.stop();
        if (result != null) {
            return Response.status(Response.Status.OK).entity(result).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @GET
    public Response get() {
        Timer.Context context = getTimer.time();
        List<Entity> result = service.getAll();
        context.stop();
        if (result != null && result.size() > 0) {
            return Response.status(Response.Status.OK).entity(result).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_JSON})
    public Response update(@PathParam("id") int id, Entity entity) {
        Timer.Context context = putTimer.time();
        entity.setId(id);
        Object result = service.update(entity);
        context.stop();
        if (result != null) {
            return Response.status(Response.Status.OK).entity(entity).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @DELETE
    @Path("{id}")
    public Response delete(@PathParam("id") int id) {
        Timer.Context context = deleteTimer.time();
        boolean isSucceeded = service.delete(id);
        context.stop();
        if (isSucceeded) {
            return Response.status(Response.Status.OK).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
}
