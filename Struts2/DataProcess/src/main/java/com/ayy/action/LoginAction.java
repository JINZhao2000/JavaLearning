package com.ayy.action;

import com.opensymphony.xwork2.Action;

/**
 * @ ClassName LoginAction
 * @ Description data process
 * @ Author Zhao JIN
 * @ Date 13/11/2020 20
 * @ Version 1.0
 */
public class LoginAction {
    private String username;
    private String password;

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String login(){
        System.out.println("username : "+username+"\tpassword : "+password);
        return Action.SUCCESS;
    }
}
