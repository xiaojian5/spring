package com.dev.custompostprocessor;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

/**
 * @author: dengxin.chen
 * @date: 2018/10/22 14:31
 * @description:
 */
public class CustomBeanFactoryPostProcessor implements BeanFactoryPostProcessor {

	@Override
	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
		System.out.println("*********调用自定义BeanFactoryPostProcessor开始*********");

		String[] beanDefinitionNames = beanFactory.getBeanDefinitionNames();

		for (String beanName : beanDefinitionNames) {
			System.out.println("bean name:" + beanName);
			if ("person".equals(beanName)) {
				BeanDefinition beanDefinition = beanFactory.getBeanDefinition(beanName);
				System.out.println("修改person的age属性");
				beanDefinition.getPropertyValues().add("age", "120");
			}
		}

		System.out.println("*********调用自定义BeanFactoryPostProcessor结束*********");

	}
}
