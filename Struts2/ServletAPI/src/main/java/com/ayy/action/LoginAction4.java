package com.ayy.action;

import com.ayy.vo.User;
import com.opensymphony.xwork2.Action;
import org.apache.struts2.interceptor.ServletRequestAware;

import javax.servlet.http.HttpServletRequest;

/**
 * @ ClassName LoginAction
 * @ Description data process by object
 * @ Author Zhao JIN
 * @ Date 14/11/2020 21
 * @ Version 1.0
 */
public class LoginAction4 implements ServletRequestAware {
    // a constructor without parameter is required
    private User user;
    private HttpServletRequest req;
    @Override
    public void setServletRequest(HttpServletRequest request) {
        this.req = request;
    }

    public String login(){
        if("abc".equals(user.getUsername())&&"1111".equals(user.getPassword())){
            req.getSession().setAttribute("currentUser",user);
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
