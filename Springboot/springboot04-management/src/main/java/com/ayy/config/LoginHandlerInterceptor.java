package com.ayy.config;


import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @ Description
 * @ Author Zhao JIN
 * @ Date 12/03/2021
 * @ Version 1.0
 */

public class LoginHandlerInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        String loginUser = (String) session.getAttribute("loginUser");
        if (loginUser == null) {
            request.setAttribute("msg","No Authentification");
            response.sendRedirect("/index.html");
            return false;
        }
        return true;
    }
}
