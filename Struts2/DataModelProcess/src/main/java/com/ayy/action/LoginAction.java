package com.ayy.action;

import com.ayy.vo.UserVO;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ModelDriven;

/**
 * @ ClassName LoginAction
 * @ Description data process by object
 * @ Author Zhao JIN
 * @ Date 14/11/2020 21
 * @ Version 1.0
 */
public class LoginAction implements ModelDriven<UserVO> {
    // a constructor without parameter is required
    private UserVO user = new UserVO();

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

    @Override
    public UserVO getModel() {
        return user;
    }
}
