package Cours2.BPP;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class myBeanPostProcessor implements BeanPostProcessor {
	@Override
	public Object postProcessBeforeInitialization (Object bean, String beanName) throws BeansException {
		System.out.println("pre");
		return bean;
	}

	@Override
	public Object postProcessAfterInitialization (Object bean, String beanName) throws BeansException {
		System.out.println("post");
		//create jdk proxy
		return Proxy.newProxyInstance(myBeanPostProcessor.class.getClassLoader(),
				bean.getClass().getInterfaces(), new InvocationHandler() {
					@Override
					public Object invoke (Object proxy, Method method, Object[] args) throws Throwable {
						System.out.println("---begin---");
						//execute
						Object obj = method.invoke(bean,args);
						System.out.println("---end---");
						return obj;
					}
				});
	}
}
