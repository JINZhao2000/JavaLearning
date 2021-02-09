package com.ayy.sessions;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @ Description
 * @ Author Zhao JIN
 * @ Date 09/02/2021
 * @ Version 1.0
 */
@WebServlet(name = "SessionServlet", value = "/sessionServlet")
public class SessionServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        session.setAttribute("uname","USER1");
        // session.setMaxInactiveInterval(60*60);
        // session.invalidate();

        // String url = response.encodeRedirectURL("/project/servletpage");
        // /project/servletpage;jsessionid=xxxxx
        // response.sendRedirect(url);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
