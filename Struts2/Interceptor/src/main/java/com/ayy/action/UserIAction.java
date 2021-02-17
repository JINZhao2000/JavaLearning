package com.ayy.action;

import com.ayy.vo.UserI;
import com.opensymphony.xwork2.Action;

/**
 * @ Description
 * @ Author Zhao JIN
 * @ Date 17/02/2021
 * @ Version 1.0
 */
public class UserIAction {
    private UserI userI;

    public UserI getUser() {
        return userI;
    }

    public void setUser(UserI userI) {
        this.userI = userI;
    }

    public String save(){
        System.out.println(userI);
        return Action.SUCCESS;
    }

    public String toSave(){
        return Action.SUCCESS;
    }
}
