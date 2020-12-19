package com.ayy.action;

/**
 * @ ClassName HelloAction
 * @ Description Hello World
 * @ Author Zhao JIN
 * @ Date 13/11/2020 12
 * @ Version 1.0
 */
public class HelloAction {
    /**
     * In struts2, all method of transaction is public without parameters
     *   return String
     *   the default method is named execute
     * @return
     */
    public String execute(){
        System.out.println("Hello World");
        return "success";
    }
}
