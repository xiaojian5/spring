package com.dev.transaction;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.dev.basebean.transaction.TransactionTestBean;

/**
 * @author: dengxin.chen
 * @date: 2019-05-27 10:31
 * @description:
 */
public class TransactionTest {

	/**
	 * 事务测试
	 */
	@Test
	public void transactionTest() {

		ApplicationContext context = new ClassPathXmlApplicationContext("classpath*:org/springframework/transaction/TransactionTest.xml");

		TransactionTestBean transactionTestBean = context.getBean(TransactionTestBean.class);

		transactionTestBean.getAge();
	}
}
