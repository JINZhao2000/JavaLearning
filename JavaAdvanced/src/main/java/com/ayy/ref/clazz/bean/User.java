package com.ayy.ref.clazz.bean;

/**
 * @ ClassName User
 * @ Description
 * @ Author Zhao JIN
 * @ Date 19/12/2020 21H
 * @ Version 1.0
 */
public class User {
    private int uid;
    private String username;
    private int age;

    public User() {}

    public User(int uid, String username, int age) {
        this.uid = uid;
        this.username = username;
        this.age = age;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
