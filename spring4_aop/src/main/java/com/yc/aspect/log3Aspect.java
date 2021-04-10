package com.yc.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @program: testspring
 * @description:
 * @avthor: Lucky
 * @create: 2021-04-10 18:53
 */
@Aspect
@Component
@Order(value = 1)
public class log3Aspect {

    @Around("execution(* com.yc.biz.StudentBizImpl.find*(..))")
    public Object compute2(ProceedingJoinPoint pjp) throws Throwable {
        System.out.println("*****************compute2进到增强了...");
        long start=System.currentTimeMillis();
        Object retVal=pjp.proceed();
        long end=System.currentTimeMillis();
        System.out.println("compute2要退出增强了");
        System.out.println("***********这个方法运行时长:"+(end-start));
        return retVal;
    }
}
