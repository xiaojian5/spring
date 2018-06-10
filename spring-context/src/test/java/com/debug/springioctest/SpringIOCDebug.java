package com.debug.springioctest;


import com.debug.basebean.User;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author: Shawn Chen
 * @date: 2018/6/6
 * @description:spring IOC调试
 */
public class SpringIoCDebug
{
    @Test
    public void testIOC()
    {
        ApplicationContext context = new ClassPathXmlApplicationContext("com/debug/config/User.xml");

        User user = (User) context.getBean("user");

        System.out.println("class name:"+user.getClass().getName());

        System.out.println("name属性:" + user.getName());
        System.out.println("gender属性:" + user.getGender());

    }
}
