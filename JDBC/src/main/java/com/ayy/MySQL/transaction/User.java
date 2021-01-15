package com.ayy.MySQL.transaction;

/**
 * @ ClassName User
 * @ Description
 * @ Author Zhao JIN
 * @ Date 15/01/2021 21H
 * @ Version 1.0
 */
public class User {
    private int uid;
    private String uname;
    private int balance;

    public User() {}

    public User(int uid, String uname, int balance) {
        this.uid = uid;
        this.uname = uname;
        this.balance = balance;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "User{"+this.uname+","+this.balance+"}";
    }
}
