package com.ayy.springaop.manuel.jdkproxy;

public class MyAspect {
	public void before(){
		System.out.println("JDK Proxy before");
	}
	public void after(){
		System.out.println("JDK Proxy after");
	}
}
