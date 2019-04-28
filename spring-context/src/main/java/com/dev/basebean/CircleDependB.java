package com.dev.basebean;

/**
 * @author: developer
 * @date: 2019/4/28 21:53
 * @description: 循环依赖演示
 */

public class CircleDependB {

	private CircleDependC circleDependC;

	public CircleDependB(CircleDependC circleDependC) {
		this.circleDependC = circleDependC;
	}

	public void show() {
		System.out.println("CircleDependB");
	}
}
