package com.dev.basebean;

/**
 * @author: dengxin.chen
 * @date: 2018/10/22 14:27
 * @description:后置处理器测试类
 */
public class Person {

	private String name;

	private Integer age;

	public void init()
	{
		System.out.println("*********Person init()开始*********");
		showInfo();
		System.out.println("*********Person init()结束*********");
	}

	public Person() {
		System.out.println("*********Person Construtor()开始*********");
		showInfo();
		System.out.println("*********Person Construtor()结束*********");
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public void showInfo() {

		System.out.println("name=" + name);
		System.out.println("age=" + age);
	}
}
