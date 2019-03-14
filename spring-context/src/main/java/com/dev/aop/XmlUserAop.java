package com.dev.aop;

import org.aspectj.lang.annotation.Aspect;

/**
 * @author: developer
 * @date: 2018/11/21 21:47
 * @description:利用
 */
@Aspect
public class XmlUserAop {

	public void beforeAdvice() {
		System.out.println();
		System.out.println("利用xml配置形式进行前置增强");
	}

	public void afterAdvice() {
		System.out.println();
		System.out.println("利用xml配置形式进行后置增强");
	}
}
