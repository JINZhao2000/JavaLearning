package com.ayy.action;

import com.ayy.vo.User;
import com.opensymphony.xwork2.Action;

import java.util.ArrayList;
import java.util.List;

/**
 * @ Description
 * @ Author Zhao JIN
 * @ Date 21/02/2021
 * @ Version 1.0
 */
public class JsonAction {
    private List<User> userList;

    public String list(){
        userList = new ArrayList<>();
        userList.add(new User(1,"USR1",21));
        userList.add(new User(2,"USR2",22));
        userList.add(new User(3,"USR3",23));
        userList.add(new User(4,"USR4",24));
        return Action.SUCCESS;
    }

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }
}
