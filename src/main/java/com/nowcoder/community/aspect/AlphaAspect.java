package com.nowcoder.community.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

/**
 * @program: community
 * @description: 实例切面
 * @author: Macchac
 * @create: 2020-07-12 13:50
 **/
//@Component
//@Aspect
public class AlphaAspect {
    /**
     * 定义切点service.*所有的service业务类.*所有的方法(..)所有参数
     */
    @Pointcut("execution(* com.nowcoder.community.service.*.*(..))")
    public void pointcut(){

    }
    /**
     * 切点之前
     */
    @Before("pointcut()")
    public void before(){
        System.out.println("before");
    }

    @After("pointcut()")
    public void after(){
        System.out.println("after");
    }
    @AfterReturning("pointcut()")
    public void afterReturning(){
        System.out.println("afterReturning");
    }
    @AfterThrowing("pointcut()")
    public void AfterThrowing(){
        System.out.println("AfterThrowing");
    }

    /**
     * 前后都植入
     * @param joinPoint 连接点
     * @return
     * @throws Throwable
     */
    @Around("pointcut()")
    public Object Around(ProceedingJoinPoint joinPoint) throws Throwable{
        System.out.println("around before");
        Object object = joinPoint.proceed();
        System.out.println("around after");
        return object;
    }
}
