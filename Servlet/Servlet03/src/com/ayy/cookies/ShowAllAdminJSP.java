package com.ayy.cookies;

import com.ayy.entities.Admin;

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
 * @ Date 07/02/2021
 * @ Version 1.0
 */
@WebServlet(value = "/showAllJSP")
public class ShowAllAdminJSP extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=UTF-8");
        List<Admin> admins = (List<Admin>) req.getAttribute("Admins");
        PrintWriter writer = resp.getWriter();
        writer.println("<html>");
        writer.println("<head>");
        writer.println("<meta charset='UTF-8'/>");
        writer.println("<title>List admins</title>");
        writer.println("</head>");
        writer.println("<body>");
        if(admins!=null){
            writer.println("<table border='1'>");
            writer.println("<tr>");
            writer.println("<td>Username</br>");
            writer.println("<td>Password</br>");
            writer.println("<td>Telephone</br>");
            writer.println("</tr>");
            for (Admin admin : admins) {
                writer.println("<tr>");
                writer.println("<td>"+admin.getUname()+"</td>");
                writer.println("<td>"+admin.getpwd()+"</td>");
                writer.println("<td>"+admin.getTel()+"</td>");
                writer.println("</tr>");
            }
            writer.println("</table>");
        }else {
            writer.println("<h1>No data</h1>");
        }
        writer.println("</body>");
        writer.println("</html>");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req,resp);
    }
}
