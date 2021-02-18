package com.ayy.vo;

/**
 * @ Description
 * @ Author Zhao JIN
 * @ Date 17/02/2021
 * @ Version 1.0
 */
public class User {
    private String uname;
    private String pwd;

    public User() {}

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    @Override
    public String toString() {
        return "User{" +
                "uname='" + uname + '\'' +
                ", pwd='" + pwd + '\'' +
                '}';
    }
}
