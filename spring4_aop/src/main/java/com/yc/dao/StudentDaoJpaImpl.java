package com.yc.dao;

import org.springframework.stereotype.Repository;

import java.util.Random;

/**
 * @program: testspring
 * @description:
 * @avthor: Lucky
 * @create: 2021-04-04 14:45
 */
@Repository
public class StudentDaoJpaImpl implements StudentDao{
    public int add(String name) {
        System.out.println("jpa添加学生:"+name);
        Random r=new Random();
        return r.nextInt();
    }

    public void update(String name) {

        System.out.println("jpa更新学生:"+name);
    }

    public void find(String name) {
        System.out.println("jpa查找学生:"+name);
    }


}
