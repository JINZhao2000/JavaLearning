package com.ayy.springaop.aspectjanno;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class MyAspect {
	@Pointcut("execution(* com.ayy.springaop.aspectjanno.UserServiceImpl.*(..))")
	private void myPointCut(){}

	@Before(value = "myPointCut()")
	public void myBefore(JoinPoint joinPoint){
		System.out.println("AspectJ Anno Before "+joinPoint.getSignature().getName());
	}

	@AfterReturning(value = "myPointCut()",returning = "object")
	public void myAfterReturning(JoinPoint joinPoint,Object object){
		System.out.println("AspectJ Anno AfterReturning "+joinPoint.getSignature().getName()+",-> "+object);
	}

	@Around(value = "myPointCut()")
	public Object myAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable{
		System.out.println("--before--");
		//execute manually
		Object obj = proceedingJoinPoint.proceed();
		System.out.println("--after--");
		return obj;
	}

	@AfterThrowing(value = "myPointCut()",throwing = "throwable")
	public void myAfterThrowing(JoinPoint joinPoint,Throwable throwable){
		System.out.println("AspectJ Anno AfterThrowing "+joinPoint.getSignature().getName());
		System.out.println(throwable.getMessage());
	}

	@After(value = "myPointCut()")
	public void myAfter(JoinPoint joinPoint){
		System.out.println("AspectJ Anno After "+joinPoint.getSignature().getName());
	}
}
