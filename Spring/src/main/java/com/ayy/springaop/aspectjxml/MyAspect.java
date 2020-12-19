package com.ayy.springaop.aspectjxml;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;

public class MyAspect {

	public void myBefore(JoinPoint joinPoint){
		System.out.println("AspectJ XML Before "+joinPoint.getSignature().getName());
	}

	public void myAfterReturning(JoinPoint joinPoint,Object object){
		System.out.println("AspectJ XML AfterReturning "+joinPoint.getSignature().getName()+",-> "+object);
	}

	public Object myAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable{
		System.out.println("--before--");
		//execute manually
		Object obj = proceedingJoinPoint.proceed();
		System.out.println("--after--");
		return obj;
	}

	public void myAfterThrowing(JoinPoint joinPoint,Throwable throwable){
		System.out.println("AspectJ XML AfterThrowing "+joinPoint.getSignature().getName());
		System.out.println(throwable.getMessage());
	}

	public void myAfter(JoinPoint joinPoint){
		System.out.println("AspectJ XML After "+joinPoint.getSignature().getName());
	}
}
