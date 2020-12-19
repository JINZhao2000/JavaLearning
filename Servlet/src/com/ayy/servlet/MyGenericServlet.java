package com.ayy.servlet;

import javax.servlet.*;
import java.io.IOException;

public class MyGenericServlet extends GenericServlet {
    @Override
    public void init (ServletConfig config) throws ServletException {
        System.out.println("My Generic Servlet init");
    }

    @Override
    public void service (ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, IOException {
        System.out.println("My Generic Servlet");
    }
}
