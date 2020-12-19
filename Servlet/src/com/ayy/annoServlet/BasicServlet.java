package com.ayy.annoServlet;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(value = {
        "/mbs",
        "/mybasicservlet"},
        loadOnStartup = 1)
public class BasicServlet extends HttpServlet {
    @Override
    public void init (ServletConfig config) throws ServletException {
        System.out.println("My Basic Servlet init");
    }

    @Override
    protected void doGet (HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("My Basic Servlet by Annotation");
    }
}
