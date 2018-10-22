package com.debug.springioctest;


import org.junit.Test;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.debug.basebean.User;

/**
 * @author: Shawn Chen
 * @date: 2018/6/6
 * @description:spring IOC调试初始化过程调试
 */
public class IoCDebug {

	/**
	 * xml形式注入bean
	 */
	@Test
	public void testIOC() {
		System.out.println("xml形式注入bean调试过程开始");
		ApplicationContext context = new ClassPathXmlApplicationContext("com/debug/config/User.xml");

		User user = (User) context.getBean("user");

		System.out.println("class name:" + user.getClass().getName());

		System.out.println("name属性:" + user.getName());
		System.out.println("gender属性:" + user.getGender());
		System.out.println("xml形式注入bean调试过程结束");
	}

	/**
	 * 注解扫描形式注入bean
	 */
	@Test
	public void testIOCAnnotation() {
		System.out.println("注解扫描形式注入bean的调试过程开始");

		ApplicationContext context = new ClassPathXmlApplicationContext("com/debug/config/beanlifecycle_annotation.xml");

		User user = (User) context.getBean("user");

		user.setName("注解");
		user.setGender("保密");

		System.out.println("class name:" + user.getClass().getName());

		System.out.println("name属性:" + user.getName());
		System.out.println("gender属性:" + user.getGender());

		System.out.println("注解扫描形式注入bean的调试过程结束");

	}
}
