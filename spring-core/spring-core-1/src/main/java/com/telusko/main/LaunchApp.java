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
    }
}
