package com.ayy.cookies;

import javax.servlet.*;
import java.io.IOException;

public class MyServlet implements Servlet {
	@Override
	public void init (ServletConfig servletConfig) throws ServletException {
		System.out.println("My Servlet init");
	}

	@Override
	public ServletConfig getServletConfig () {
		return null;
	}

	@Override
	public void service (ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, IOException {
		System.out.println("My First Servlet");
	}

	@Override
	public String getServletInfo () {
		return null;
	}

	@Override
	public void destroy () {

	}
}
