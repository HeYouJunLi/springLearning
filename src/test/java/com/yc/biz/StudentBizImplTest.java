package com.yc.biz;

import com.yc.AppConfig;
import com.yc.dao.StudentDao;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.junit.Assert.*;

public class StudentBizImplTest {

    private ApplicationContext ac;
    private StudentBizImpl biz;

    @Before
    public void setUp() throws Exception {
        ac=new AnnotationConfigApplicationContext(AppConfig.class);
        biz= (StudentBizImpl) ac.getBean("studentBizImpl");
    }

    @Test
    public void add() {
        biz.add("张三");
    }

    @Test
    public void update() {
        biz.update("张三");
    }
}