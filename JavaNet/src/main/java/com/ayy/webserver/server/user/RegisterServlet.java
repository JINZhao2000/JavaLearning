package com.ayy.webserver.server.user;

import com.ayy.webserver.server.Request;
import com.ayy.webserver.server.Response;
import com.ayy.webserver.server.Servlet;

/**
 * @ ClassName RegisterServlet
 * @ Description
 * @ Author Zhao JIN
 * @ Date 11/01/2021 22H
 * @ Version 1.0
 */
public class RegisterServlet implements Servlet {
    @Override
    public void service(Request request, Response response) {
        response.add("Register success");
    }
}
