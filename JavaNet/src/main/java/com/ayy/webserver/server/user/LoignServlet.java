package com.ayy.webserver.server.user;

import com.ayy.webserver.server.Request;
import com.ayy.webserver.server.Response;
import com.ayy.webserver.server.Servlet;

import java.io.IOException;
import java.io.InputStream;

/**
 * @ ClassName LoignServlet
 * @ Description
 * @ Author Zhao JIN
 * @ Date 11/01/2021 22H
 * @ Version 1.0
 */
public class LoignServlet implements Servlet {
    @Override
    public void service(Request request, Response response) {
        try {
            InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("login.html");
            byte[] data = new byte[1024 * 1024];
            int len = 0;
            len = is.read(data);
            response.add(new String(data, 0, len));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
