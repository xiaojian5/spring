package com.dev.ioc;


import org.junit.Test;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.dev.basebean.aware.UserDefinedAware;
import com.dev.basebean.ioc.MultiConditionBean;
import com.dev.basebean.circledepend.setter.SetterCircleDependA;

/**
 * @author: Shawn Chen
 * @date: 2018/6/6
 * @description:spring IOC调试初始化过程调试
 */
public class IOCTest {

	/**
	 * xml形式注入bean
	 */
	@Test
	public void xmlIOCTest() {

		System.out.println("xml形式注入bean调试过程开始");
		// classpath*:com/dev/config/*
		ApplicationContext context = new ClassPathXmlApplicationContext("classpath*:com/dev/config/ioc/multicondition.xml");

		MultiConditionBean multiConditionBean = (MultiConditionBean) context.getBean("multiConditionBean");

		System.out.println("class name:" + multiConditionBean.getClass().getName());

		System.out.println("name属性:" + multiConditionBean.getName());
		System.out.println("gender属性:" + multiConditionBean.getGender());
		System.out.println("placeHolderValue属性:" + multiConditionBean.getPlaceHolderValue());

		System.out.println("xml形式注入bean调试过程结束");
	}

	/**
	 * 注解扫描形式注入bean
	 */
	@Test
	public void annotationIOCTest() {

		System.out.println("注解扫描形式注入bean的调试过程开始");

		ApplicationContext context = new ClassPathXmlApplicationContext("com/dev/config/beanlife/beanlife_annotation.xml");

		MultiConditionBean user = (MultiConditionBean) context.getBean("multiConditionBean");

		user.setName("注解");
		user.setGender("保密");

		System.out.println("class name:" + user.getClass().getName());

		System.out.println("name属性:" + user.getName());
		System.out.println("gender属性:" + user.getGender());

		System.out.println("注解扫描形式注入bean的调试过程结束");

	}

	/**
	 * 构造器循环依赖注入测试 会抛出BeanCreationException异常
	 */
	@Test(expected = BeanCreationException.class)
	public void constructorCircleDependTest() {
		new ClassPathXmlApplicationContext("classpath*:com/dev/config/circledepend/circledepend_constructor.xml");
	}

	/**
	 * setter循环依赖注入测试 不会抛出异常
	 */
	@Test
	public void setterCircleDependTest() {
		ApplicationContext context = new ClassPathXmlApplicationContext("classpath*:com/dev/config/circledepend/circledepend_setter.xml");
		SetterCircleDependA setterCircleDependA = (SetterCircleDependA) context.getBean("setterCircleDependA");
		System.out.println(setterCircleDependA.getClass());
	}

	/**
	 * Aware接口演示
	 */
	@Test
	public void awareTest() {
		ApplicationContext context = new ClassPathXmlApplicationContext("classpath*:com/dev/config/aware/aware.xml");
		UserDefinedAware userDefinedAware = context.getBean(UserDefinedAware.class);
		userDefinedAware.showMsg();
	}
}
