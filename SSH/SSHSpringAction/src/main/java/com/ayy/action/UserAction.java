package com.ayy.action;

import com.ayy.bean.User;
import com.ayy.service.UserService;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

/**
 * @ Description
 * @ Author Zhao JIN
 * @ Date 22/02/2021
 * @ Version 1.0
 */
public class UserAction extends ActionSupport implements ModelDriven<User> {
    private User user = new User();

    @Override
    public User getModel() {
        return user;
    }

    private UserService service;

    public void setService(UserService service) {
        this.service = service;
    }

    public String register(){
        service.register(user);
        return Action.SUCCESS;
    }
}
