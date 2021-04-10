package com.yc;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Date;

/**
 * @program: testspring
 * @description:
 * @avthor: Lucky
 * @create: 2021-04-10 20:12
 */
public class LogAspct implements InvocationHandler {

    private Object target;

    public LogAspct(Object target){
        this.target=target;
    }

    public Object createProxy(){
        return Proxy.newProxyInstance(this.target.getClass().getClassLoader(),this.target.getClass().getInterfaces(),this);
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        System.out.println("代理类的对象:"+proxy.getClass());
        System.out.println("目标类的方法:"+method);
        System.out.println("方法中的参数:"+args);

        if(method.getName().startsWith("add")){
            //前置增强
        }

        Object returnValue=method.invoke(this.target,args);

        //后置增强

        return returnValue;
    }

    private void log(){
        System.out.println("=======before advice======");
        System.out.println("hello,this is"+new Date());
        System.out.println("==========");
    }
}
