package com.ayy.action;

import com.ayy.vo.User;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;

import java.util.Map;

/**
 * @ ClassName LoginAction
 * @ Description data process by object
 * @ Author Zhao JIN
 * @ Date 14/11/2020 21
 * @ Version 1.0
 */
public class LoginAction {
    // a constructor without parameter is required
    private User user;

    public String login(){
        if("abc".equals(user.getUsername())&&"1111".equals(user.getPassword())){
            ActionContext.getContext().getSession().put("currentUser",user);
            ActionContext.getContext().getApplication();
            Map<String,Object> req = (Map<String, Object>) ActionContext.getContext().get("request");
            req.put("pwd",user.getPassword());
            return Action.SUCCESS;
        }
        System.out.println("username : "+user.getUsername()+"\tpassword : "+user.getPassword());
        return Action.LOGIN;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
