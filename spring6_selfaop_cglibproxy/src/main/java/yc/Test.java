package yc;

import yc.biz.StudentBizImpl;

/**
 * @program: testspring
 * @description:
 * @avthor: Lucky
 * @create: 2021-04-10 20:21
 */
public class Test {

    public static void main(String[] args) {
        StudentBizImpl target=new StudentBizImpl();

        LogAspct la=new LogAspct(target);

        Object obj = la.createProxy();

        if(obj instanceof StudentBizImpl){
            StudentBizImpl sb= (StudentBizImpl) obj;

            sb.find("张三");

            sb.add("李四");

            sb.update("王五");
        }
    }
}
