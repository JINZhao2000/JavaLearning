package com.ayy.MySQL.dbutils;

/**
 * @ ClassName Good
 * @ Description
 * @ Author Zhao JIN
 * @ Date 17/01/2021 18H
 * @ Version 1.0
 */
public class Good {
    private int gid;
    private String name;

    public Good() {}

    public Good(int gid, String name) {
        this.gid = gid;
        this.name = name;
    }

    public int getGid() {
        return gid;
    }

    public void setGid(int gid) {
        this.gid = gid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Good{"+this.name+"}";
    }
}
