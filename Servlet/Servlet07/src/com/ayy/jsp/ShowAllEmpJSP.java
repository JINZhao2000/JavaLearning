package com.ayy.jsp;

import com.ayy.entities.Emp;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * @ Description
 * @ Author Zhao JIN
 * @ Date 12/02/2021
 * @ Version 1.0
 */
@WebServlet(value = "/manager/safe/showAllEmpJSP")
public class ShowAllEmpJSP extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Emp> emps = (List<Emp>) req.getAttribute("emps");
        PrintWriter writer = resp.getWriter();

        writer.println("<html><head><meta charset='UTF-8'/>");
        writer.println("<title>AllEmp</title></head>");
        writer.println("<body>");
        writer.println("<table border='1'>");
        writer.println("<tr><td>ID</td><td>Name</td><td>Salary</td><td>Age</td><td colspan='2'>Operations</td></tr>");
        for (Emp emp : emps) {
            writer.println("<tr><td>");
            writer.println(emp.getEid());
            writer.println("</td><td>");
            writer.println(emp.getEname());
            writer.println("</td><td>");
            writer.println(emp.getSalary());
            writer.println("</td><td>");
            writer.println(emp.getAge());
            writer.println("</td><td><a href='"+req.getContextPath()+"/manager/safe/showEmpController?eid="+emp.getEid()+"'>Modify</a></td>");
            writer.println("<td><a href='"+req.getContextPath()+"/manager/safe/removeEmpController?eid="+emp.getEid()+"'>Delete</a></td><tr>");
        }
        writer.println("</table>");
        writer.println("</body></html>");
    }
}
