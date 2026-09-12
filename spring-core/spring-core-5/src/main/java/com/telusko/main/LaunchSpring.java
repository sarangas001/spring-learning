package com.telusko.main;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class LaunchSpring {
    public static void main(String[] args) {

        ApplicationContext container = new ClassPathXmlApplicationContext("appconfig.xml");

        Telusko telusko = container.getBean(Telusko.class);

        telusko.buyTheCourse(999.99);

    }
}
