package com.ayy.springaop.manuel.jdkproxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class MyBeanFactory {
	public static UserService createService(){
		//target
		final UserService userService = new UserServiceImpl();
		//aspect
		final MyAspect myAspect = new MyAspect();
		//proxy
		UserService proxyService = (UserService) Proxy.newProxyInstance(
				MyBeanFactory.class.getClassLoader(),//ClassLoader
				//1. Factory.class.getClassLoader();
				//2. AImpl.getClass().getClassLoader();
				new Class[]{UserService.class},//interfaces the proxy implement
				//1. AImpl.getClass().getInterfaces(); can only catch its owns interfaces (not parent's interfaces)
				//2. new Class[]{A.class}
				new InvocationHandler() {
					@Override
					public Object invoke (Object proxy, Method method, Object[] args) throws Throwable {
						//final needed
						//before
						myAspect.before();
						//method.getName()
						//method.invoke(Object object,Object[] args) of Impl
						Object obj = method.invoke(userService,args);
						//after
						myAspect.after();
						return obj;
					}
					//invoke(this,addUser,[])
				}
				//invocationHandler -> unnamed inner class
				//when every method runs, will call the method invoke()
		);
		return proxyService;
	}
}
