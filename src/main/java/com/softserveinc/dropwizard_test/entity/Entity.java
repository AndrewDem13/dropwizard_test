package com.softserveinc.dropwizard_test.entity;

public class Entity {

    private String message;

    public Entity() {
    }

    public Entity(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
