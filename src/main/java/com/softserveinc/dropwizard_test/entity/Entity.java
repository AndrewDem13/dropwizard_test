package com.softserveinc.dropwizard_test.entity;

import java.io.Serializable;

public class Entity implements Serializable{

    private int id;
    private String message;

    public Entity() {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Entity entity = (Entity) o;

        if (getId() != entity.getId()) return false;
        return getMessage() != null ? getMessage().equals(entity.getMessage()) : entity.getMessage() == null;
    }

    @Override
    public int hashCode() {
        return getId();
    }
}
