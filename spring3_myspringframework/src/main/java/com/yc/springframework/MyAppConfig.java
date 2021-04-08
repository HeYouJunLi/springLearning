package com.yc.springframework;

import com.yc.bean.HelloWorld;
import com.yc.springframework.stereotype.MyBean;
import com.yc.springframework.stereotype.MyComponentScan;
import com.yc.springframework.stereotype.MyConfiguration;

/**
 * @program: testspring
 * @description:
 * @avthor: Lucky
 * @create: 2021-04-05 14:28
 */
@MyConfiguration
@MyComponentScan(basePackages = {"com.yc.bean"})
public class MyAppConfig {

    @MyBean
    public HelloWorld hw(){
        return new HelloWorld();
    }

    @MyBean
    public HelloWorld hw2(){
        return new HelloWorld();
    }

}
