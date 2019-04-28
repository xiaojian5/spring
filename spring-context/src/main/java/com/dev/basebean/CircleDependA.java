package com.dev.basebean;

/**
 * @author: developer
 * @date: 2019/4/28 21:53
 * @description: 循环依赖演示
 */

public class CircleDependA {

	private CircleDependB circleDependB;

	/**
	 * 演示构造器的循环依赖 会直接抛出异常
	 *
	 * @param circleDependB
	 */
	public CircleDependA(CircleDependB circleDependB) {
		this.circleDependB = circleDependB;
	}

	public void show() {
		System.out.println("CircleDependA");
	}
}
