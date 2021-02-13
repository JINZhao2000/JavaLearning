package com.ayy.jsp;

import com.ayy.entities.Emp;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @ Description
 * @ Author Zhao JIN
 * @ Date 13/02/2021
 * @ Version 1.0
 */
@WebServlet(value = "/manager/safe/showUpdateEmpInfoJSP")
public class ShowUpdateEmpInfoJSP extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Emp emp = (Emp) req.getAttribute("emp");
        PrintWriter writer = resp.getWriter();
        writer.println("<html><head><meta charset='UTF-8'/>");
        writer.println("<title>Emp modify</title></head>");
        writer.println("<body>");

        writer.println("<form action='./updateEmpController' method='post'>");
        writer.println("EID:    <input type='text' name='eid' readonly='true' value='"+emp.getEid()+"'><br/>");
        writer.println("Ename:  <input type='text'   name='ename'  value='"+emp.getEname()+ "'><br/>");
        writer.println("Salary: <input type='number' name='salary' value='"+emp.getSalary()+"'><br/>");
        writer.println("Age:    <input type='number' name='age'    value='"+emp.getAge()+   "'><br/>");
        writer.println("<input type='submit' value='modify'><br/>");
        writer.println("</form>");

        writer.println("</body></html>");
    }
}
