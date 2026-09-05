package com.telusko.main;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class LaunchApp
{
    public static void main(String[] args)
    {
        //BeanFactory --> Many
        //ApplicationConext
        ApplicationContext container = new ClassPathXmlApplicationContext("appconfig-xml.xml");

//        Telusko telusko = container.getBean(Telusko.class);
//        Telusko telusko = (Telusko) container.getBean("dev");
        Telusko telusko = container.getBean( Telusko.class);
        telusko.buyTheCourse(399.9);

    }
}
