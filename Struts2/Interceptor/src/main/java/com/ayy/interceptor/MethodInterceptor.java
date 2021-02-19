package com.ayy.interceptor;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.MethodFilterInterceptor;

/**
 * @ Description
 * @ Author Zhao JIN
 * @ Date 19/02/2021
 * @ Version 1.0
 */
public class MethodInterceptor extends MethodFilterInterceptor {
    @Override
    protected String doIntercept(ActionInvocation invocation) throws Exception {
        System.out.println("Method Interceptor");
        return invocation.invoke();
    }
}
