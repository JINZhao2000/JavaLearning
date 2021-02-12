package com.ayy.controller;

import com.ayy.entities.EmpManager;
import com.ayy.service.EmpManagerService;
import com.ayy.service.impl.EmpManagerServiceImpl;

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
 * @ Date 12/02/2021
 * @ Version 1.0
 */
@WebServlet("/manager/EmpManagerLoginController")
public class EmpManagerLoginController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();

        String uname = req.getParameter("uname");
        String pwd = req.getParameter("pwd");

        EmpManagerService service = new EmpManagerServiceImpl();
        EmpManager empManager = service.login(uname, pwd);

        if(empManager!=null){
            session.setAttribute("mgr",empManager);
            resp.sendRedirect(req.getContextPath()+"/manager/safe/showAllEmpController");
        }else {
            resp.sendRedirect(req.getContextPath()+"/login.html");
        }
    }
}
