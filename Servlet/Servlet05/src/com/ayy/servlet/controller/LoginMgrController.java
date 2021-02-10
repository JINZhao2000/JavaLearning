package com.ayy.servlet.controller;

import com.ayy.entities.Manager;
import com.ayy.service.impl.ManagerServiceImpl;

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
 * @ Date 10/02/2021
 * @ Version 1.0
 */
@WebServlet(value = "/loginMgr")
public class LoginMgrController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html;charset=UTF-8");

        String uname = req.getParameter("uname");
        String pwd = req.getParameter("pwd");

        Manager manager = new ManagerServiceImpl().login(uname,pwd);

        if(manager!=null){
            HttpSession session = req.getSession();
            session.setAttribute("mgr",manager);
            resp.sendRedirect("/servlet5/showAllController");
        }else {
            resp.sendRedirect("/servlet5/loginMgr.html");
        }
    }
}
