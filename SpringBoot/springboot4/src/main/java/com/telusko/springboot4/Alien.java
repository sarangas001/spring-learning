package com.telusko.springboot4;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.stereotype.Component;

@Component
public class Alien {
    static {
        System.out.println("1 - Static Block");
    }
    {
        System.out.println("2 - Non Static Block");
    }

    public Alien() {
        System.out.println("3 - constructor");
    }

    @PostConstruct
    public void init() {
        System.out.println("Post constructor method");
    }

    public static void method() {
        System.out.println("4 - Static normal method");
    }

   public void method2() {
       System.out.println("5 - Normal Method");
   }

   @PreDestroy
   public void destory() {
       System.out.println("Pre Destroy method");
   }
}
