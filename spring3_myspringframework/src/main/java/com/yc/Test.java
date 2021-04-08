package com.yc;

import com.yc.bean.HelloWorld;
import com.yc.springframework.MyAppConfig;
import com.yc.springframework.context.MyAnnotationConfigApplicationConext;
import com.yc.springframework.context.MyApplicationContext;

/**
 * @program: testspring
 * @description:
 * @avthor: Lucky
 * @create: 2021-04-05 14:19
 */
public class Test {
    public static void main(String[] args) {
        MyApplicationContext ac = new MyAnnotationConfigApplicationConext(MyAppConfig.class);
        HelloWorld hw= (HelloWorld) ac.getBean("hw");
        hw.show();
    }
}
