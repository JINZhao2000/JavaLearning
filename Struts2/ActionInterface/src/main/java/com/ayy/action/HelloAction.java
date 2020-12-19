package com.ayy.action;

import com.opensymphony.xwork2.Action;

/**
 * @ ClassName HelloAction
 * @ Description create Action by implementing Action Interface
 * @ Author Zhao JIN
 * @ Date 14/11/2020 22
 * @ Version 1.0
 */
public class HelloAction implements Action {
    @Override
    public String execute() throws Exception {
        System.out.println("Action Interface");
        return Action.SUCCESS;
    }
}
