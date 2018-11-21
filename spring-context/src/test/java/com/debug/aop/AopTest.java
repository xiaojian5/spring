package com.debug.aop;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.debug.basebean.User;

/**
 * @author: dengxin.chen
 * @date: 2018/11/21 13:41
 * @description: Aop测试
 */
public class AopTest {

	/**
	 * aop测试
	 */
	@Test
	public void testAop() {

		System.out.println("Aop调试开始");

		ApplicationContext context = new ClassPathXmlApplicationContext("com/debug/config/User.xml");

		User user = (User) context.getBean("user");

		user.sayHello();

		System.out.println("Aop调试结束");

	}
}
