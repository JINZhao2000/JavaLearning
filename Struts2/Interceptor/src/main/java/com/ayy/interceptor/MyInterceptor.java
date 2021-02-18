package com.ayy.interceptor;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;

/**
 * @ Description
 * @ Author Zhao JIN
 * @ Date 18/02/2021
 * @ Version 1.0
 */
public class MyInterceptor implements Interceptor {
    @Override
    public void destroy() {

    }

    @Override
    public void init() {

    }

    @Override
    public String intercept(ActionInvocation invocation) throws Exception {
        System.out.println("My interceptor is executed");
        return invocation.invoke();
    }
}
