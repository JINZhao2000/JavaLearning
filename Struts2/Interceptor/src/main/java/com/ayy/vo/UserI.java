package com.ayy.vo;

/**
 * @ Description
 * @ Author Zhao JIN
 * @ Date 17/02/2021
 * @ Version 1.0
 */
public class UserI {
    private String uname;
    private int age;

    public UserI() {}

    public UserI(String uname, int age) {
        this.uname = uname;
        this.age = age;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "UserI{" +
                "uname='" + uname + '\'' +
                ", age=" + age +
                '}';
    }
}
