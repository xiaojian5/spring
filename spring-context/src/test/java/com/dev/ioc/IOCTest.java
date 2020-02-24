package com.dev.ioc;


import org.junit.Test;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.dev.basebean.aware.UserDefinedAware;
import com.dev.basebean.beanpostprocessor.BeanPostProcessorBase;
import com.dev.basebean.circledepend.setter.SetterCircleDependA;
import com.dev.basebean.initializingbean.UserDefinedInitializingBean;
import com.dev.basebean.ioc.AnnotationIocTestBean;
import com.dev.basebean.ioc.IocTestBean;
import com.dev.basebean.lifecycle.BeanLifeCycle;
import com.dev.basebean.lookupmethod.impl.ShowCar;


/**
 * @author: Shawn Chen
 * @date: 2018/6/6
 * @description:spring IOC调试初始化过程调试
 */
public class IOCTest {
	@Autowired
	private IocTestBean iocTestBean;

	public void setIocTestBean(IocTestBean iocTestBean){
		this.iocTestBean = iocTestBean;
	}

	/**
	 * xml形式注入bean
	 */
	@Test
	public void xmlIOCTest() {



		System.out.println("xml形式注入bean调试过程开始");
		// classpath*:com/dev/config/*
		ApplicationContext context = new ClassPathXmlApplicationContext("classpath*:com/dev/config/ioc/ioc.xml");

		System.out.println(iocTestBean);
//		((ClassPathXmlApplicationContext) context).getBeanFactory().registerSingleton();

		IocTestBean iocTestBean = (IocTestBean) context.getBean("iocTestBean");

		Object value = ((ClassPathXmlApplicationContext) context).getBeanFactory().getBeanDefinition("iocTestBean").getAttribute("metaKey");

		System.out.println("<meta> value=" + value);

		System.out.println("class name:" + iocTestBean.getClass().getName());

		System.out.println("name属性:" + iocTestBean.getName());
		System.out.println("gender属性:" + iocTestBean.getGender());
		System.out.println("placeHolderValue属性:" + iocTestBean.getPlaceHolderValue());

		System.out.println("xml形式注入bean调试过程结束");
	}

	/**
	 * 注解形式注入bean
	 */
	@Test
	public void annotationIOCTest() {

		System.out.println("注解形式注入bean调试过程开始");

		ApplicationContext context = new ClassPathXmlApplicationContext("classpath*:com/dev/config/ioc/annotation_ioc.xml");

		AnnotationIocTestBean annotationIocTestBean = context.getBean(AnnotationIocTestBean.class);

		annotationIocTestBean.sayHello();

		System.out.println("注解形式注入bean调试过程结束");
	}

	/**
	 * 构造器循环依赖注入测试 会抛出BeanCreationException异常
	 */
	@Test(expected = BeanCreationException.class)
	public void constructorCircleDependTest() {
		new ClassPathXmlApplicationContext("classpath*:com/dev/config/circledepend/circledepend_constructor.xml");
	}

	/**
	 * setter循环依赖注入测试 不会抛出异常
	 */
	@Test
	public void setterCircleDependTest() {
		ApplicationContext context = new ClassPathXmlApplicationContext("classpath*:com/dev/config/circledepend/circledepend_setter.xml");
		SetterCircleDependA setterCircleDependA = (SetterCircleDependA) context.getBean("setterCircleDependA");
		System.out.println(setterCircleDependA.getClass());
	}

	/**
	 * Aware接口演示
	 */
	@Test
	public void awareTest() {
		ApplicationContext context = new ClassPathXmlApplicationContext("classpath*:com/dev/config/aware/aware.xml");
		UserDefinedAware userDefinedAware = context.getBean(UserDefinedAware.class);
		userDefinedAware.showMsg();
	}

	/**
	 * BeanPostProcessor演示
	 * 这里用ClassPathXmlApplicationContext进行演示不能只单独使用UserDefinedBeanPostProcessor做演示，因为出现BeanPostProcessor类会优先进行创建，从而得不到演示效果
	 * 其具体优先创建bean的点{@link org.springframework.context.support.PostProcessorRegistrationDelegate#registerBeanPostProcessors} 278行
	 * 如果要单独使用UserDefinedBeanPostProcessor，则加载的方式必须发生变化才行
	 */
	@Test
	public void beanPostProcessorTest() {

	/*	ClassPathResource resource = new ClassPathResource("com/dev/config/beanpostprocessor/beanpostprocessor.xml");
		DefaultListableBeanFactory factory = new DefaultListableBeanFactory();
		XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(factory);
		reader.loadBeanDefinitions(resource);
		// 单独使用UserDefinedBeanPostProcessor必须手动进行注册，然后就必须手动new一个对象，这种方式并不友好。
		UserDefinedBeanPostProcessor definedBeanPostProcessor = new UserDefinedBeanPostProcessor();
		factory.addBeanPostProcessor(definedBeanPostProcessor);
		UserDefinedBeanPostProcessor test = (UserDefinedBeanPostProcessor) factory.getBean("userDefinedBeanPostProcessor");
		test.showMsg();*/
		ApplicationContext context = new ClassPathXmlApplicationContext("com/dev/config/beanpostprocessor/beanpostprocessor.xml");
		BeanPostProcessorBase postProcessor = context.getBean(BeanPostProcessorBase.class);
		System.out.println(postProcessor.getMsg());
	}

	/**
	 * InitializingBean测试
	 */
	@Test
	public void initializingBeanTest() {
		ApplicationContext context = new ClassPathXmlApplicationContext("classpath*:com/dev/config/initializingbean/initializingbean.xml");
		UserDefinedInitializingBean initializingBean = context.getBean(UserDefinedInitializingBean.class);
		System.out.println(initializingBean.getMsg());
	}

	/**
	 * bean生命周期测试
	 */
	@Test
	public void beanLifeCycleTest() {
		ApplicationContext context = new ClassPathXmlApplicationContext("classpath*:com/dev/config/lifecycle/lifecycle.xml");
		BeanLifeCycle beanLifeCycle = context.getBean(BeanLifeCycle.class);
		System.out.println("msg=" + beanLifeCycle.getMsg());
		((ClassPathXmlApplicationContext) context).close();
	}

	/**
	 * PropertyOverrideConfigurer测试
	 */
	@Test
	public void propertyoverrideconfigurerTest() {
		ApplicationContext context = new ClassPathXmlApplicationContext("classpath*:com/dev/config/propertyoverrideconfigurer/propertyoverride.xml");
		IocTestBean conditionBean = context.getBean(IocTestBean.class);
		System.out.println(conditionBean.getName());
	}

	/**
	 * lookup-method标签测试
	 */
	@Test
	public void lookupmethodTest() {
		ApplicationContext context = new ClassPathXmlApplicationContext("classpath*:com/dev/config/ioc/lookupmethod.xml");

		ShowCar showCar = context.getBean(ShowCar.class);

		showCar.showCarInfo();
	}
}
