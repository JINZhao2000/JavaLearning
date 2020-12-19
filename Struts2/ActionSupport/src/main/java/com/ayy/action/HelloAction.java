package com.ayy.action;

import com.opensymphony.xwork2.ActionSupport;

/**
 * @ ClassName HelloAction
 * @ Description create Action by extending ActionSupport
 * @ Provided by ActionSupport : Data Validation, Globalization, Localization
 * @ Author Zhao JIN
 * @ Date 14/11/2020 22
 * @ Version 1.0
 */
public class HelloAction extends ActionSupport {
    @Override
    public String execute() throws Exception {
        System.out.println("ActionSupport Class");
        return super.execute();
    }
}
