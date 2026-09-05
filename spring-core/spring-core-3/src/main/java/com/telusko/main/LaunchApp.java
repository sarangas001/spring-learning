package com.telusko.main;

import com.telusko.config.Password;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class LaunchApp
{
    public static void main(String[] args)
    {
        //BeanFactory --> Many
        //ApplicationConext
        ApplicationContext container = new ClassPathXmlApplicationContext("appconfig.xml");
        Password pass = container.getBean(Password.class);
        System.out.println(pass.algoInfo());
    }
}
