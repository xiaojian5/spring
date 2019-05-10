package com.dev.basebean.aop;

/**
 * @author: dengxin.chen
 * @date: 2019/5/10 14:53
 * @description:aop xml 演示bean
 */
public class UserDefinedAopXmlBean {

	private Integer age;
	private String name;

	public void aopTest(String msg) {
		System.out.println(msg);
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
