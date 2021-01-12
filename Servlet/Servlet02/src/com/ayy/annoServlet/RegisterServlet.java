package com.ayy.annoServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @ ClassName RegisterServlet
 * @ Description
 * @ Author Zhao JIN
 * @ Date 11/01/2021 23H
 * @ Version 1.0
 */
@WebServlet(value="/rs")
public class RegisterServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // get data from request
        String username = req.getParameter("username");
        // username = new String(username.getBytes("ISO8859-1"),"UTF-8");
        String pwd = req.getParameter("pwd");
        System.out.println("GET username: "+username+"\tpwd: "+pwd);
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String username = req.getParameter("username");
        String pwd = req.getParameter("pwd");
        System.out.println("POST username: "+username+"\tpwd: "+pwd);

//        resp.setCharacterEncoding("UTF-8");
//        resp.setHeader("Content-type","text/html;charset=UTF-8");
        resp.setContentType("text/html;charset=UTF-8");
        PrintWriter writer = resp.getWriter();
        writer.println("register success!");
        writer.println("中文");
    }
}

