package com.ayy.counter;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @ Description
 * @ Author Zhao JIN
 * @ Date 11/02/2021
 * @ Version 1.0
 */
@WebServlet(value = "/showCounterController")
public class ShowCounterController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletContext servletContext = req.getServletContext();
        Integer counter = (Integer) servletContext.getAttribute("counter");
        if(counter == null){
            counter = 0;
        }
        counter++;
        servletContext.setAttribute("counter",counter);
    }
}
