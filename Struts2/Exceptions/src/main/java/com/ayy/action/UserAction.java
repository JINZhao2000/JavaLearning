package com.ayy.action;

import com.ayy.exception.UserException;
import com.ayy.service.UserService;
import com.ayy.service.impl.UserServiceImpl;
import com.opensymphony.xwork2.Action;

/**
 * @ Description
 * @ Author Zhao JIN
 * @ Date 22/02/2021
 * @ Version 1.0
 */
public class UserAction {
    private UserService userService = new UserServiceImpl();
    // private String msg;
    public String delete() throws UserException {
        userService.delete(0);
        return Action.SUCCESS;
    }
}
