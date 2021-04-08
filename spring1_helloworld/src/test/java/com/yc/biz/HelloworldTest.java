package com.yc.biz;

import com.yc.AppConfig;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.junit.Assert.*;

public class HelloworldTest {
    private ApplicationContext ac;

    @Before
    public void setUp() throws Exception {
        ac=new AnnotationConfigApplicationContext(AppConfig.class);
    }

    @Test
    public void hello() {
        Helloworld hw= (Helloworld) ac.getBean("helloworld");
        hw.hello();
    }
}