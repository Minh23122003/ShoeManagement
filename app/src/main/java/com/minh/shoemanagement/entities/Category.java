package com.minh.shoemanagement.entities;

import java.io.Serializable;


//implement Serializable for bind object Category between 2 Activities
public class Category implements Serializable {
    private long id;
    private String name;

    public Category(){

    }
    public Category(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
