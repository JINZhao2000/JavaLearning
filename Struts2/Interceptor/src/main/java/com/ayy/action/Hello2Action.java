package com.ayy.action;

import com.opensymphony.xwork2.Action;

/**
 * @ Description
 * @ Author Zhao JIN
 * @ Date 16/02/2021
 * @ Version 1.0
 */
public class Hello2Action {
    public String hello(){
        System.out.println("Called");
        return Action.SUCCESS;
    }
}
