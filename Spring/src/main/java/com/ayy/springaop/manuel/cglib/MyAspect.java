package com.ayy.springaop.manuel.cglib;

public class MyAspect {
	public void before(){
		System.out.println("CGLIB before");
	}
	public void after(){
		System.out.println("CGLIB after");
	}
}
