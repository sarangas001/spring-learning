package com.telusko.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.time.LocalTime;

@Aspect
@Component
public class AlienAOP
{

    private LocalTime beforeT;

    @Before(value = "execution(* com.telusko.controller.*.*(..))")
    public void beforeAdvice(JoinPoint joinPoint)
    {
        System.out.println(
                "Before method: " + joinPoint.getSignature() + " at " + LocalTime.now()
        );
        this.beforeT = LocalTime.now();
    }

    @After(value = "execution(* com.telusko.controller.*.*(..))")
    public void afterAdvice(JoinPoint joinPoint)
    {
        System.out.println(
                "After method: " + joinPoint.getSignature() + " at " + LocalTime.now()
        );
        System.out.println("Differences of the time -> " + LocalTime.now().compareTo(beforeT));
    }

    @After(value = "execution(* com.telusko.service.*.*(..))")
    public void afterAdviceService(JoinPoint joinPoint)
    {
        System.out.println(
                "After method: " + joinPoint.getSignature() + " at " + LocalTime.now()
        );
    }
}
