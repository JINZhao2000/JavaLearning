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
public class UserAction extends ActionSupport {
    private User user;

    public String execute(){
        System.out.println(user);
        return Action.SUCCESS;
    }

    @Override
    public void validate() {
        if (user.getUsername().length()<4 || user.getUsername().length()>10) {
            this.addFieldError("user.username","Error of username");
        }
        if (user.getAge() <= 0) {
            this.addFieldError("user.age","Error of age");
        }
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
