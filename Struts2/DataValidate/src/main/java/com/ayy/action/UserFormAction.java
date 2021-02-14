package com.ayy.action;

import com.ayy.vo.User;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;

/**
 * @ Description
 * @ Author Zhao JIN
 * @ Date 14/02/2021
 * @ Version 1.0
 */
public class UserFormAction extends ActionSupport {
    private User user;

    public String save(){
        System.out.println(user);
        return Action.SUCCESS;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
