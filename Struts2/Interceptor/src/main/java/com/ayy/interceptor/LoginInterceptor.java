package com.ayy.interceptor;

import com.ayy.vo.User;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

/**
 * @ Description
 * @ Author Zhao JIN
 * @ Date 18/02/2021
 * @ Version 1.0
 */
public class LoginInterceptor extends AbstractInterceptor {
    @Override
    public String intercept(ActionInvocation invocation) throws Exception {
        String actionName = invocation.getProxy().getActionName();
        if(actionName.equals("login")) {
            return invocation.invoke();
        }
        User user = (User) invocation.getInvocationContext().getSession().get("user");
        if(null!=user){
            return invocation.invoke();
        }
        return Action.LOGIN;
    }
}
