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
import java.util.List;

/**
 * @ Description
 * @ Author Zhao JIN
 * @ Date 12/02/2021
 * @ Version 1.0
 */
@WebServlet(value = "/manager/safe/showAllEmpController")
public class ShowAllEmpController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        EmpService empService = new EmpServiceImpl();
        List<Emp> empList = empService.showAllEmp();
        req.setAttribute("emps",empList);
        req.getRequestDispatcher("/manager/safe/showAllEmpJSP").forward(req, resp);
    }
}
