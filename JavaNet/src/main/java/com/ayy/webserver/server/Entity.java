package com.ayy.webserver.server;

/**
 * @ ClassName Entity
 * @ Description
 * @ Author Zhao JIN
 * @ Date 11/01/2021 22H
 * @ Version 1.0
 */
public class Entity {
    private String name;
    private String cls;

    public Entity() {}

    public Entity(String name, String cls) {
        this.name = name;
        this.cls = cls;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCls() {
        return cls;
    }

    public void setCls(String cls) {
        this.cls = cls;
    }

    @Override
    public String toString() {
        return "Entity{name: "+name+", class: "+cls+"}";
    }
}
