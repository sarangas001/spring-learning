package com.telusko.main;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class LaunchSpring {
    public static void main(String[] args) {

        // xml
//        ApplicationContext container = new ClassPathXmlApplicationContext("appconfig.xml");

        // only using java annotation approach
        ApplicationContext container = new AnnotationConfigApplicationContext(Config.class);

        Telusko telusko = container.getBean(Telusko.class);

        telusko.buyTheCourse(999.99);

    }
}
