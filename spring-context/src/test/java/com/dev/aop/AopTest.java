package com.dev.aop;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.dev.basebean.ioc.MultiConditionBean;

/**
 * @author: dengxin.chen
 * @date: 2018/11/21 13:41
 * @description: Aop测试
 */
public class AopTest {

	/**
	 * 基于配置形式的aop增强测试
	 */
	@Test
	public void testXmlAop() {

		System.out.println("xml配置形式Aop调试开始");

		ApplicationContext context = new ClassPathXmlApplicationContext("com/dev/config/aop/aop_xml.xml");

		MultiConditionBean user = (MultiConditionBean) context.getBean("multiConditionBean");

		user.sayHello();

		System.out.println();
		System.out.println("xml配置形式Aop调试结束");

	}

	/**
	 * 基于注解形式的aop测试
	 */
	@Test
	public void testAnnotationAop() {

		System.out.println("注解形式Aop调试开始");

		ApplicationContext context = new ClassPathXmlApplicationContext("com/dev/config/aop/aop_annotation.xml");

		MultiConditionBean user = (MultiConditionBean) context.getBean("multiConditionBean");

		user.sayHello();

		System.out.println();
		System.out.println("注解形式Aop调试结束");

	}
}
