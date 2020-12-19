package com.ayy.jdbctemplate.dbcp;

/**
 * @ ClassName User
 * @ Description JavaBean User
 * @ Author Zhao JIN
 * @ Date 30/10/2020 20:32
 * @ Version 1.0
 */
public class User {
    private  Integer Id;
    private  String username;
    private  String password;

    public User () {}

    public User (Integer id, String username, String password) {
        Id = id;
        this.username = username;
        this.password = password;
    }

    public Integer getId () {
        return Id;
    }

    public void setId (Integer id) {
        Id = id;
    }

    public String getUsername () {
        return username;
    }

    public void setUsername (String username) {
        this.username = username;
    }

    public String getPassword () {
        return password;
    }

    public void setPassword (String password) {
        this.password = password;
    }

    @Override
    public String toString () {
        return "User{" +
                "Id=" + Id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
