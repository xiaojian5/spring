package com.dev.aop;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.dev.basebean.aop.UserDefinedAopAnnotationBean;
import com.dev.basebean.aop.UserDefinedAopXmlBean;

/**
 * @author: dengxin.chen
 * @date: 2018/11/21 13:41
 * @description: Aop测试
 */
public class AopTest {

	/**
	 * 基于xml配置形式的aop测试
	 */
	@Test
	public void xmlAopTest() {

		System.out.println("基于xml配置形式Aop测试开始");

		ApplicationContext context = new ClassPathXmlApplicationContext("com/dev/config/aop/xml_aop.xml");

		UserDefinedAopXmlBean userDefinedAopBean = context.getBean(UserDefinedAopXmlBean.class);

		userDefinedAopBean.aopTest("aop test");

		System.out.println();
		System.out.println("age=" + userDefinedAopBean.getAge() + " name=" + userDefinedAopBean.getName());
		System.out.println();
		System.out.println("基于xml配置形式Aop测试结束");

	}

	/**
	 * 基于注解形式的aop测试
	 */
	@Test
	public void annotationAopTest() {

		System.out.println("基于注解形式Aop测试开始");

		ApplicationContext context = new ClassPathXmlApplicationContext("com/dev/config/aop/annotation_aop.xml");

		UserDefinedAopAnnotationBean userDefinedAopAnnotationBean = context.getBean(UserDefinedAopAnnotationBean.class);

		userDefinedAopAnnotationBean.aopTest("annotation aop test");

		System.out.println();
		System.out.println("基于注解形式Aop测试结束");

	}
}
