package com.ayy.entities;

/**
 * @ Description
 * @ Author Zhao JIN
 * @ Date 10/02/2021
 * @ Version 1.0
 */
public class Manager {
    private String uname;
    private String pwd;

    public Manager() {}

    public Manager(String uname, String pwd) {
        this.uname = uname;
        this.pwd = pwd;
    }

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
        return "Manager{" +
                "uname='" + uname + '\'' +
                ", pwd='" + pwd + '\'' +
                '}';
    }
}
