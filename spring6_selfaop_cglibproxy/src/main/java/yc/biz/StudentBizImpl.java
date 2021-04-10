package yc.biz;

/**
 * @program: testspring
 * @description:
 * @avthor: Lucky
 * @create: 2021-04-10 20:09
 */
public class StudentBizImpl{
    public int add(String name) {
        System.out.println("调用了studentbizimpl中的add"+name);
        return 100;
    }

    public void update(String name) {
        System.out.println("调用了studentbizimpl中的update"+name);

    }

    public String find(String name) {
        System.out.println("调用了studentbizimpl中的find"+name);

        return name;
    }
}
