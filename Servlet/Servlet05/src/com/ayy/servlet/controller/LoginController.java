package com.ayy.servlet.controller;

import com.ayy.entities.Admin;
import com.ayy.service.AdminService;
import com.ayy.service.impl.AdminServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @ Description
 * @ Author Zhao JIN
 * @ Date 10/02/2021
 * @ Version 1.0
 */
@WebServlet(value = "/loginController")
public class LoginController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html; chatset=UTF-8");

        String uname = req.getParameter("uname");
        String pwd = req.getParameter("pwd");

        AdminService service = new AdminServiceImpl();
        Admin admin = service.login(uname, pwd);
        req.setAttribute("admin",admin);
        req.getRequestDispatcher("/loginJSP").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
