package com.softserveinc.dropwizard_test.db.impl;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.DeleteResult;
import com.softserveinc.dropwizard_test.db.EntityDao;
import com.softserveinc.dropwizard_test.entity.Entity;
import org.bson.Document;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

public class MongoEntityDao extends EntityDao {

    private final MongoDatabase mongoDatabase;
    private MongoCollection<Entity> collection;

    @Inject
    public MongoEntityDao(MongoDatabase mongoDatabase) {
        this.mongoDatabase = mongoDatabase;
        this.collection = mongoDatabase.getCollection("entities", Entity.class);
    }

    @Override
    public void createEntity(Entity entity) {
        collection.insertOne(entity);
    }

    @Override
    public Entity getEntity(String message) {
        return collection.find(new Document("message", message)).first();
    }

    @Override
    public List<Entity> getAll() {
        List<Entity> result = new ArrayList<>();
        FindIterable<Entity> entities = collection.find();
        for (Entity entity : entities) {
            result.add(entity);
        }
        return result;
    }

    @Override
    public Entity updateEntity(String message, Entity entity) {
        return collection.findOneAndReplace(new Document("message",message), entity);
    }

    @Override
    public boolean deleteEntity(String message) {
        DeleteResult result = collection.deleteOne(new Document("message", message));
        return result.getDeletedCount() > 0;
    }
}
