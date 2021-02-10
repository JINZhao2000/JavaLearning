package com.ayy.servlet.controller;

import com.ayy.entities.Admin;
import com.ayy.entities.Manager;
import com.ayy.service.AdminService;
import com.ayy.service.impl.AdminServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * @ Description
 * @ Author Zhao JIN
 * @ Date 07/02/2021
 * @ Version 1.0
 */
@WebServlet(value = "/showAllController")
public class ShowAllAdminController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Manager mgr = (Manager) session.getAttribute("mgr");
        if(mgr==null){
            resp.sendRedirect("/servlet5/loginMgr.html");
            return;
        }
        AdminService service = new AdminServiceImpl();
        List<Admin> admins = service.showAllAdmin();
        req.setAttribute("Admins",admins);
        req.getRequestDispatcher("/showAllJSP").forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
