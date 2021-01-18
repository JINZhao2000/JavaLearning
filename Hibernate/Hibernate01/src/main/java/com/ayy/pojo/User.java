package com.ayy.pojo;

/**
 * @ ClassName User
 * @ Description
 * @ Author Zhao JIN
 * @ Date 17/01/2021 22H
 * @ Version 1.0
 */
public class User {
    private Integer uid;
    private String uname;
    private Integer balance;

    public User() {}

    public User(Integer uid, String uname, Integer balance) {
        this.uid = uid;
        this.uname = uname;
        this.balance = balance;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public Integer getBalance() {
        return balance;
    }

    public void setBalance(Integer balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "User{"+this.uname+","+this.balance+"}";
    }
}
