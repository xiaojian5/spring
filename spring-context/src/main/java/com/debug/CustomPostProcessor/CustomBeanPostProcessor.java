package com.debug.CustomPostProcessor;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

import com.debug.basebean.Person;

/**
 * @author: dengxin.chen
 * @date: 2018/10/22 15:39
 * @description:
 */
public class CustomBeanPostProcessor implements BeanPostProcessor {
	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		System.out.println("*********Bean实例化之前执行（start）*********");
		System.out.println("before:" + beanName);
		if ("person".equals(beanName)) {
			Person person = (Person) bean;
			person.setName("测试姓名");
		}
		System.out.println("*********Bean实例化之前执行（end）*********");
		return bean;
	}

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		System.out.println("*********Bean实例化之后执行（start）*********");
		System.out.println("after:" + beanName);
		if ("person".equals(beanName)) {
			Person person = (Person) bean;
			person.setName("修改测试姓名");
			person.setAge(999);
		}
		System.out.println("*********Bean实例化之后执行（end）*********");
		return bean;
	}
}
