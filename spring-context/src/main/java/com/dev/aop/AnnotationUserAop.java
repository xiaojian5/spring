package com.dev.aop;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * @author: developer
 * @date: 2018/11/21 21:47
 * @description:利用
 */
@Aspect
@Component
public class AnnotationUserAop {

	@Pointcut("execution(* com.dev.basebean.ioc.MultiConditionBean.sayHello(..))")
	private void pointcutMethod() {

	}

	@Before("pointcutMethod()")
	public void beforeAdvice() {
		System.out.println();
		System.out.println("利用【注解】形式进行前置增强");
	}

	@After("pointcutMethod()")
	public void afterAdvice() {
		System.out.println();
		System.out.println("利用【注解】形式进行后置增强");
	}
}
