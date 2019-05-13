package com.gogoyang.lifecapsule.controller;

import org.springframework.stereotype.Repository;

@Repository
public class Tank {
    private String _id;
    private String name;
    private String weight;
    private String color;
    private String musice;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getMusice() {
        return musice;
    }

    public void setMusice(String musice) {
        this.musice = musice;
    }
}
