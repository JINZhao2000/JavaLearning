package com.ayy.entities;

/**
 * @ Description
 * @ Author Zhao JIN
 * @ Date 05/02/2021
 * @ Version 1.0
 */
public class Admin {
    private String uname;
    private String pwd;
    private String tel;

    public Admin() {}

    public Admin(String uname, String pwd, String tel) {
        this.uname = uname;
        this.pwd = pwd;
        this.tel = tel;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getpwd() {
        return pwd;
    }

    public void setpwd(String pwd) {
        this.pwd = pwd;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    @Override
    public String toString() {
        return "Admin{" +
                "uname='" + uname + '\'' +
                ", pwd='" + pwd + '\'' +
                ", tel='" + tel + '\'' +
                '}';
    }
}
