package com.dev.basebean.ioc;

import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Repository;

/**
 * @author: Shawn Chen
 * @date: 2018/6/6
 * @description:
 */
@Repository
public class MultiConditionBean implements InitializingBean, BeanNameAware, BeanClassLoaderAware {
	private String name;

	private String gender;

	private String placeHolderValue;

	public void setPlaceHolderValue(String placeHolderValue) {
		this.placeHolderValue = placeHolderValue;
	}

	public String getPlaceHolderValue() {
		return placeHolderValue;
	}

	public void sayHello() {
		System.out.println("Hello Aop");
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	@Override
	public void setBeanClassLoader(ClassLoader classLoader) {
		System.out.println("Enter MultiConditionBean.setBeanClassLoader(ClassLoader classLoader)");
	}

	@Override
	public void setBeanName(String name) {
		System.out.println("Enter MultiConditionBean.setBeanName(String name)");
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		System.out.println("Enter MultiConditionBean.afterPropertiesSet()");
	}

	public void initMethod() {
		System.out.println("Enter MultiConditionBean.initMethod()");
	}
}
