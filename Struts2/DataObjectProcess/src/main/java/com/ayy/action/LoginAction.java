package com.ayy.action;

import com.ayy.vo.UserVO;
import com.opensymphony.xwork2.Action;

/**
 * @ ClassName LoginAction
 * @ Description data process by object
 * @ Author Zhao JIN
 * @ Date 14/11/2020 21
 * @ Version 1.0
 */
public class LoginAction {
    // a constructor without parameter is required
    private UserVO user;

    public String login(){
        System.out.println("username : "+user.getUsername()+"\tpassword : "+user.getPassword());
        return Action.SUCCESS;
    }

    public UserVO getUser() {
        return user;
    }

    public void setUser(UserVO user) {
        this.user = user;
    }
}
