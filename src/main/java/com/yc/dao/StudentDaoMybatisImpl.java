package com.yc.dao;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.Random;

/**
 * @program: testspring
 * @description:
 * @avthor: Lucky
 * @create: 2021-04-04 14:47
 */
@Repository
public class StudentDaoMybatisImpl implements StudentDao{

    public int add(String name) {
        System.out.println("mybatis添加学生:"+name);
        Random r=new Random();
        return r.nextInt();
    }

    public void update(String name) {

        System.out.println("mybatis更新学生:"+name);
    }
}
