package com.yc.biz;

import com.yc.dao.StudentDao;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @program: testspring
 * @description:
 * @avthor: Lucky
 * @create: 2021-04-04 14:49
 */
@Service
public class StudentBizImpl implements StudentBiz{
    private StudentDao dao;

    public StudentBizImpl(StudentDao dao) {
        this.dao = dao;
    }

    public StudentBizImpl() {
    }

    //@Inject  //@Named("studentDaoJpaImpl")
//    @Autowired
//    @Qualifier("studentDaoJpaImpl")
    @Resource(name = "studentDaoJpaImpl")
    public void setDao( StudentDao dao) {
        this.dao = dao;
    }

    public int add(String name) {
        System.out.println("======业务层======");
        System.out.println("用户名是否重名");
        int result=dao.add(name);
        System.out.println("====业务操作结束====");
        return result;
    }

    public void update(String name) {
        System.out.println("======业务层======");
        System.out.println("用户名是否重名");
        dao.update(name);
        System.out.println("====业务操作结束====");
    }

    public void find(String name) {
        System.out.println("======业务层======");
        System.out.println("用户名是否重名");
        dao.find(name);
        System.out.println("====业务操作结束====");
    }
}
