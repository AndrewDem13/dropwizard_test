package com.softserveinc.dropwizard_test.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Entity {

    @JsonProperty
    private long id;

    @JsonProperty
    private String message;

    public Entity() {
    }

    public Entity(long id, String message) {
        this.id = id;
        this.message = message;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
