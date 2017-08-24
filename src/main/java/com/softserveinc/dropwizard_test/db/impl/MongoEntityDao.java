package com.softserveinc.dropwizard_test.db.impl;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.DeleteResult;
import com.softserveinc.dropwizard_test.db.CrudDao;
import com.softserveinc.dropwizard_test.entity.Entity;
import org.bson.Document;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

public class MongoEntityDao implements CrudDao<Entity> {

    private final MongoDatabase mongoDatabase;
    private MongoCollection<Entity> collection;

    @Inject
    public MongoEntityDao(MongoDatabase mongoDatabase) {
        this.mongoDatabase = mongoDatabase;
    }

    @PostConstruct
    void postConstruct() {
        String collectionName = Entity.class.getSimpleName().toLowerCase();
        this.collection = mongoDatabase.getCollection(collectionName, Entity.class);
    }

    @Override
    public void create(Entity entity) {
        collection.insertOne(entity);
    }

    @Override
    public Entity get(int id) {
        return collection.find(new Document("_id", id)).first();
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
    public Entity update(int id, Entity entity) {
        return collection.findOneAndReplace(new Document("_id",id), entity);
    }

    @Override
    public boolean delete(int id) {
        DeleteResult result = collection.deleteOne(new Document("_id", id));
        return result.getDeletedCount() > 0;
    }
}
