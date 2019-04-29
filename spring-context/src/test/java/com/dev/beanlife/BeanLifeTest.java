package com.dev.beanlife;

import com.dev.basebean.Person;
import com.dev.beanlifecycle.BeanLifeCycle;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author: Shawn Chen
 * @date: 2018/6/6
 * @description:Bean生命周期调试
 */
public class BeanLifeTest {

	@Test
	public void testBeanLife() {

		System.out.println("Bean生命周期：");
		System.out.println("Spring容器初始化");
		System.out.println("=====================================");

		ApplicationContext context = new ClassPathXmlApplicationContext("com/dev/config/beanlife/beanlife_xml.xml");

		System.out.println("Spring容器初始化完毕");
		System.out.println("=====================================");

		System.out.println("从容器中获取Bean");
		System.out.println();

		BeanLifeCycle beanLifeCycle = (BeanLifeCycle) context.getBean("beanlifecycle");

		System.out.println("15.初始化成功，并且属性注入成功：beanLifyCycle Name=" + beanLifeCycle.getName());
		System.out.println("=====================================");

		((ClassPathXmlApplicationContext) context).close();
		System.out.println("=====================================");
		System.out.println("Spring容器关闭，Bean生命周期结束！");

	}

	@Test
	public void testBeanFactroy() {
		ApplicationContext context = new ClassPathXmlApplicationContext("com/dev/config/beancustomfactory.xml");

		//构造函数执行两次是因为在使用反射实例化对象的时候多了一行相同的代码，现在在源码中间该行代码屏蔽了
		//  Object object= ctor.newInstance(args);
		Person person = (Person) context.getBean("person");

		person.showInfo();

		System.out.println("BeanFactoryPostProcessor的执行顺序在构造函数之前");
		System.out.println("BeanPostProcessor#postProcessBeforeInitialization的在构造函数之后，init-method方法之前执行");
		System.out.println("BeanPostProcessor#postProcessAfterInitialization的在init-method方法之后执行");

	}

}
