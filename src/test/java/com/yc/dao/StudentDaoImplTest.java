package com.yc.dao;

import com.yc.biz.StudentBizImpl;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class StudentDaoImplTest {
    private  StudentDao dao;
    private StudentBizImpl biz;

    @Before
    public void setUp() throws Exception {
        dao=new StudentDaoJpaImpl();

        //biz=new StudentBizImpl(dao);

        biz=new StudentBizImpl();
        biz.setDao(dao);
    }

    @Test
    public void add() {
        dao.add("张三");
    }

    @Test
    public void update() {
        dao.update("张三");
    }

    @Test
    public void bizadd() {
        biz.add("张三");
    }
}