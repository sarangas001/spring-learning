package com.telusko.main;

import com.telusko.service.ServiceLayer;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;

public class LaunchSpring {
    public static void main(String[] args) {

        // There has difference between using Application Context and BeanFactory ,
        // 1. Application Context - Create everything beans and others if it's not using or not. - Eger
        // 2. BeanFactory - Create beans or others when it's needed - memory efficient way - Lazy

        // Default SpringBoot (Spring + Extra Features) using the Application Context
        // But SpringBoot needed to be more efficient , SpringBoot achieve that using Scopes

//        ApplicationContext container = new ClassPathXmlApplicationContext("appconfig.xml");

        DefaultListableBeanFactory factory = new DefaultListableBeanFactory();

        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(factory);

        reader.loadBeanDefinitions(new ClassPathResource("appconfig.xml"));

        ServiceLayer serviceLayer = factory.getBean(ServiceLayer.class);
        serviceLayer.display();

    }
}
