package com.ayy.controller;

import com.ayy.entities.Emp;
import com.ayy.service.EmpService;
import com.ayy.service.impl.EmpServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @ Description
 * @ Author Zhao JIN
 * @ Date 13/02/2021
 * @ Version 1.0
 */
@WebServlet(value = "/manager/safe/showEmpController")
public class ShowEmpController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int eid = 0;
        try {
            eid = Integer.parseInt(req.getParameter("eid"));
        }catch (Exception e){
            resp.sendRedirect(req.getContextPath()+"/manager/safe/showAllEmpController");
            return;
        }
        if(eid == 0){
            resp.sendRedirect(req.getContextPath()+"/manager/safe/showAllEmpController");
            return;
        }
        EmpService service = new EmpServiceImpl();
        Emp emp = service.showEmp(eid);
        req.setAttribute("emp",emp);
        req.getRequestDispatcher("/manager/safe/showUpdateEmpInfoJSP").forward(req, resp);
    }
}
