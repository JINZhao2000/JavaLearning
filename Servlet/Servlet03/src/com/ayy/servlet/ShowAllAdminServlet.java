package com.ayy.servlet;

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
import java.util.List;

/**
 * @ Description
 * @ Author Zhao JIN
 * @ Date 06/02/2021
 * @ Version 1.0
 */
@WebServlet(value = "/show")
public class ShowAllAdminServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset='UTF-8'");
        AdminService service = new AdminServiceImpl();
        List<Admin> admins = service.showAllAdmin();
        PrintWriter writer = resp.getWriter();
        writer.println("<html>");
        writer.println("<head>");
        writer.println("<meta charset='UTF-8'/>");
        writer.println("<title>List admins</title>");
        writer.println("</head>");
        writer.println("<body>");
        if(admins!=null){

        }else {
            writer.println("<h1>No data</h1>");
        }
        writer.println("</body>");
        writer.println("</html>");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
