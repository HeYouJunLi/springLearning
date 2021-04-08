package com.yc.dao;

import com.yc.springframework.stereotype.MyRepository;

import java.util.Random;

/**
 * @program: testspring
 * @description:
 * @avthor: Lucky
 * @create: 2021-04-04 14:47
 */
@MyRepository
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
