package com.yc.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @program: testspring
 * @description:
 * @avthor: Lucky
 * @create: 2021-04-09 20:13
 */
@Aspect
@Component
@Order(value = 100)
public class LogAspect {

    @Around("execution(* com.yc.biz.StudentBizImpl.find*(..))")
    public Object compute(ProceedingJoinPoint pjp) throws Throwable {
        System.out.println("*****************compute进到增强了...");
        long start=System.currentTimeMillis();
        Object retVal=pjp.proceed();
        long end=System.currentTimeMillis();
        System.out.println("compute要退出增强了");
        System.out.println("***********这个方法运行时长:"+(end-start));
        return retVal;
    }

    @Pointcut("execution(* com.yc.biz.StudentBizImpl.add*(..))")
    private void add(){

    }

    @Pointcut("execution(* com.yc.biz.StudentBizImpl.update*(..))")
    private void update(){

    }

    @Pointcut("add() || update()")
    private void addAndUpdate(){

    }


    @Before("com.yc.aspect.LogAspect.addAndUpdate()")
    public void log(){
        System.out.println("==========前置增强的日志==========");
        Date d=new Date();
        SimpleDateFormat sdf=new SimpleDateFormat("yyy-MM-dd HH:mm:ss");
        String dstr=sdf.format(d);
        System.out.println("执行时间:"+dstr);
        System.out.println("========前置增强的日志结束=========");
    }


}
