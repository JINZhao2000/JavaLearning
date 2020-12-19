package com.ayy.springaop.semi_automatic;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

public class MyAspect implements MethodInterceptor {
	@Override
	public Object invoke (MethodInvocation invocation) throws Throwable {
		System.out.println("Spring semi-auto before");
		Object obj = invocation.proceed();
		System.out.println("Spring semi-auto after");
		return obj;
	}
}
