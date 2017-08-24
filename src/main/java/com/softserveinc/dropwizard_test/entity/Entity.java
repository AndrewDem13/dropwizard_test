package com.softserveinc.dropwizard_test.entity;

import java.util.Random;

public class Entity {

    private int id;
    private String message;

    public Entity() {
    }

    public Entity(String message) {
        this.message = message;
        id = new Random().nextInt(1000);
    }

    public Entity(int id, String message) {
        this.id = id;
        this.message = message;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
