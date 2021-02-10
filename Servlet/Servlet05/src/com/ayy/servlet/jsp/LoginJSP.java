package com.ayy.servlet.jsp;

import com.ayy.entities.Admin;

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
 * @ Date 10/02/2021
 * @ Version 1.0
 */
@WebServlet(value = "/loginJSP")
public class LoginJSP extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Admin admin = (Admin) req.getAttribute("admin");

        PrintWriter writer = resp.getWriter();

        writer.println("<html>");
        writer.println("<head>");
        writer.println("<meta charset='UTF-8'/>");
        writer.println("<title>Result</title>");
        writer.println("</head>");
        writer.println("<body>");
        if(null!=admin){
            writer.println("<h1>Success</h1>");
        }else {
            writer.println("<h1>Failed</h1>");
        }
        writer.println("</body>");
        writer.println("</html>");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
