package com.ayy.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

/**
 * @ Description
 * @ Author Zhao JIN
 * @ Date 11/02/2021
 * @ Version 1.0
 */
@WebFilter(value = "/target")
public class MyFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("Filter Begin");
        filterChain.doFilter(servletRequest, servletResponse);
        System.out.println("Filter End");
    }

    @Override
    public void destroy() {}
}
