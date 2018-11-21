package com.debug.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

/**
 * @author: dengxin.chen
 * @date: 2018/11/21 13:43
 * @description:对User类进行aop增强
 */
@Aspect
public class UserAop {

	@Before(value = "execution(* com.debug.basebean.User.sayHello(..))")
	public void beforeAdvice() {
		System.out.println();
		System.out.println("对user的sayHello方法进行前置增强");
	}
}
