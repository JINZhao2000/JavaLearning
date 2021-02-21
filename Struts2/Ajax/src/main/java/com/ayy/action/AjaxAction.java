package com.ayy.action;

import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @ Description
 * @ Author Zhao JIN
 * @ Date 21/02/2021
 * @ Version 1.0
 */
public class AjaxAction {
    private String username;

    public String checkName() throws IOException {
        HttpServletResponse resp = ServletActionContext.getResponse();
        if("USR1".equals(username)){
            resp.getWriter().print("true");
        }else {
            resp.getWriter().print("false");
        }
        return null;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
