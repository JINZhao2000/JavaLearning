package com.ayy.springaop.manuel.cglib;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class MyBeanFactory {
	public static UserServiceImpl createService(){
		//target
		final UserServiceImpl userServiceImpl = new UserServiceImpl();
		//aspect
		final MyAspect myAspect = new MyAspect();
		//proxy
		//1. Enhancer
		Enhancer enhancer = new Enhancer();
		//2. choose the parent's class
		enhancer.setSuperclass(userServiceImpl.getClass());
		//3 set callback === invocationHandler
		enhancer.setCallback(new MethodInterceptor() {
			@Override
			// intercept === invoke
			//proxy method args methodProxy
			public Object intercept (Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
				//before
				myAspect.before();
				//execute target method
				Object obj = method.invoke(userServiceImpl,objects);
				//execute the parent of proxy
				//methodProxy.invokeSuper(o,objects);
				//after
				myAspect.after();
				return obj;
			}
		});
		//4. create proxy
		UserServiceImpl proxyService = (UserServiceImpl) enhancer.create();
		return proxyService;
	}
}
