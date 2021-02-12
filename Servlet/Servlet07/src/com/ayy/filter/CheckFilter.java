package com.ayy.filter;

import com.ayy.entities.EmpManager;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @ Description
 * @ Author Zhao JIN
 * @ Date 12/02/2021
 * @ Version 1.0
 */
@WebFilter(value = "/manager/safe/*")
public class CheckFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;
        HttpSession session = req.getSession();
        EmpManager mgr = (EmpManager) session.getAttribute("mgr");
        if(mgr!=null){
            filterChain.doFilter(req, resp);
        }else {
            resp.sendRedirect(req.getContextPath()+"/login.html");
        }
    }

    @Override
    public void destroy() {

    }
}
