package com.dev.basebean;

/**
 * @author: developer
 * @date: 2019/4/28 21:53
 * @description: 循环依赖演示
 */

public class CircleDependC {

	private CircleDependA circleDependA;

	public CircleDependC(CircleDependA circleDependA) {
		this.circleDependA = circleDependA;
	}

	public void show() {
		System.out.println("CircleDependC");
	}
}
