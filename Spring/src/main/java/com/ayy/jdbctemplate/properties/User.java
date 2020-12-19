package com.ayy.jdbctemplate.properties;

/**
 * @ ClassName User
 * @ Description JavaBean User
 * @ Author Zhao JIN
 * @ Date 30/10/2020 20:32
 * @ Version 1.0
 */

/**
 * UpperCase needed to replaced the '_<Letter>' in tables of sql
 */
public class User {
    private  Integer Id;
    private  String sUsername;
    private  String sPassword;

    public User () {}

    public User (Integer Id, String sPassword, String sUsername) {
        this.Id = Id;
        this.sPassword = sPassword;
        this.sUsername = sUsername;
    }

    public Integer getId () {
        return Id;
    }

    public void setId (Integer Id) {
        this.Id = Id;
    }

    public String getsPassword () {
        return sPassword;
    }

    public void setsPassword (String sPassword) {
        this.sPassword = sPassword;
    }

    public String getsUsername () {
        return sUsername;
    }

    public void setsUsername (String sUsername) {
        this.sUsername = sUsername;
    }

    @Override
    public String toString () {
        return "User{" +
                "Id=" + Id +
                ", sPassword='" + sPassword + '\'' +
                ", sUsername='" + sUsername + '\'' +
                '}';
    }
}
