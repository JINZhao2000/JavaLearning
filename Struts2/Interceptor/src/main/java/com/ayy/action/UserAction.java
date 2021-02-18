package com.ayy.action;

import com.ayy.vo.User;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;

/**
 * @ Description
 * @ Author Zhao JIN
 * @ Date 17/02/2021
 * @ Version 1.0
 */
public class UserAction {
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String save(){
        System.out.println(user);
        return Action.SUCCESS;
    }

    public String login(){
        if("asd".equals(user.getUname())&&"123".equals(user.getPwd())) {
            ActionContext.getContext().getSession().put("user",user);
            return Action.SUCCESS;
        }
        return Action.LOGIN;
    }

    public String toLogin(){
        return Action.SUCCESS;
    }

    public String toSave(){
        return Action.SUCCESS;
    }
}
