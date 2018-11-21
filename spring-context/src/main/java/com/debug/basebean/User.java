package com.debug.basebean;

import org.springframework.stereotype.Repository;

/**
 * @author: Shawn Chen
 * @date: 2018/6/6
 * @description:
 */
@Repository
public class User {
	private String name;

	private String gender;

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
}
