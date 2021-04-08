package com.yc.biz;

import org.springframework.stereotype.Component;

/**
 * @program: testspring
 * @description:
 * @avthor: Lucky
 * @create: 2021-04-04 15:21
 */
@Component
public class Helloworld {
    public Helloworld() {
        System.out.println("无参构造方法");
    }

    public void hello(){
        System.out.println("hello world");
    }
}
