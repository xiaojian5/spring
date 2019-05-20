package com.dev.transaction;

import javax.transaction.Transactional;

import org.springframework.tests.sample.beans.TestBean;

/**
 * @author: dengxin.chen
 * @date: 2019/5/20 16:32
 * @description:
 */
public class TransactionTest extends TestBean {

	@Override
	@Transactional
	public Object returnsThis() {
		return super.returnsThis();
	}
}
