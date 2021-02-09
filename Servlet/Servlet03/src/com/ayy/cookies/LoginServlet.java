package com.ayy.cookies;

import com.ayy.entities.Admin;
import com.ayy.service.AdminService;
import com.ayy.service.impl.AdminServiceImpl;

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
 * @ Date 06/02/2021
 * @ Version 1.0
 */

@WebServlet(value = "/login")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html; chatset=UTF-8");

        String uname = req.getParameter("uname");
        String pwd = req.getParameter("pwd");

        AdminService service = new AdminServiceImpl();
        Admin admin = service.login(uname, pwd);
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
        doGet(req,resp);
    }
}
