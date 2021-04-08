package com.yc.springframework.context;

import com.yc.springframework.stereotype.*;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;

/**
 * @program: testspring
 * @description:
 * @avthor: Lucky
 * @create: 2021-04-05 14:21
 */
public class MyAnnotationConfigApplicationConext implements MyApplicationContext{

    private Map<String,Object> beanMap=new HashMap<String, Object>();

    public MyAnnotationConfigApplicationConext(Class<?>... componentClasses){
        try {
            register(componentClasses);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private  void register(Class<?>[] componentClasses) throws Exception {
        //请实现这个里面的代码
        //1实现IOC,MyPostConstruct,MyPreDestroy
        if(componentClasses == null || componentClasses.length<=0){
            throw new RuntimeException("没有指定配置类");
        }
        for(Class cl:componentClasses){
            if(!cl.isAnnotationPresent(MyConfiguration.class)){
                continue;
            }

            String[] basePackages=getAppConfigBasePackages(cl);
            if(cl.isAnnotationPresent(MyConfiguration.class)){
                MyComponentScan mcs= (MyComponentScan) cl.getAnnotation(MyComponentScan.class);
                if(mcs.basePackages()!=null && mcs.basePackages().length>0){
                    basePackages=mcs.basePackages();
                }
            }

            Object obj=cl.newInstance();
            handleAtMyBean(cl,obj);

            for(String basePackage:basePackages){
                scanPackageAndSubPackageClasses(basePackage);
            }

            //继续其他托管baen
            handleManagedBean();

            //2实现DI
            handleDi(beanMap);
        }

    }

    /*
    循环 beanMap中的每个bean,找到它们每个类中的每个由@Autowired @Resource注解的方法以实现DI
     */
    private void handleDi(Map<String, Object> beanMap) throws InvocationTargetException, IllegalAccessException {
        Collection<Object> objectCollection=beanMap.values();
        for(Object obj:objectCollection){
            Class cls=obj.getClass();
            Method[] ms=cls.getDeclaredMethods();
            for(Method m:ms){
                if(m.isAnnotationPresent(MyAutowired.class) && m.getName().startsWith("set")){
                    invokeAutowiredMethod(m,obj);
                }else if(m.isAnnotationPresent(MyResource.class) && m.getName().startsWith("set")){
                    invokeResourceMethod(m,obj);
                }
            }

            Field[] fs=cls.getDeclaredFields();
            for(Field field:fs){
                if(field.isAnnotationPresent(MyAutowired.class)){

                }else if (field.isAnnotationPresent(MyResource.class)){

                }
            }
        }
    }

    private void invokeResourceMethod(Method m,Object obj) throws InvocationTargetException, IllegalAccessException {
        //1.取出MyResource中的name属性值,当成 beanId
        MyResource mr=m.getAnnotation(MyResource.class);
        String beanId=mr.name();
        //2.如果没有，则取出m方法中参数的类型名，改成首字母小写 当成beanId
        if(beanId==null || beanId.equalsIgnoreCase("")){
            String pname=m.getParameterTypes()[0].getSimpleName();
            beanId=pname.substring(0,1).toLowerCase()+pname.substring(1);
        }
        //3.从beanMap取出
        Object o=beanMap.get(beanId);
        m.invoke(obj,o);

    }

    private void invokeAutowiredMethod(Method m,Object obj) throws InvocationTargetException, IllegalAccessException {
        //1.取出m的参数的类型
        Class typeClass=m.getParameterTypes()[0];
        //2.从beanMap中循环所有的object
        Set<String> keys=beanMap.keySet();
        for (String key:keys){
            Object o=beanMap.get(key);
            //判断这些object是否为   参数类型的实例 instanceof
            Class[] interfaces=o.getClass().getInterfaces();
            for(Class c:interfaces){
                System.out.println(c.getName()+"\t"+typeClass);
                if(c==typeClass){
                    m.invoke(obj,o);
                    break;
                }
            }

        }

    }


    /**
     * 处理managedBeanClasses 所有的Class类，筛选出所有的@Component @Service @Repository的类，并实例化，存到beanMao中
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws InstantiationException
     */
    private void handleManagedBean() throws IllegalAccessException, InvocationTargetException, InstantiationException {
        for(Class c:managedBeanClasses){
            if(c.isAnnotationPresent(MyComponent.class)){
                saveManagedBean(c);
            }else if(c.isAnnotationPresent(MyService.class)){
                saveManagedBean(c);
            }else if(c.isAnnotationPresent(MyRepository.class)){
                saveManagedBean(c);
            }else if(c.isAnnotationPresent(MyController.class)){
                saveManagedBean(c);
            }else {
                continue;
            }
        }
    }

    private void saveManagedBean(Class c) throws IllegalAccessException, InstantiationException, InvocationTargetException {
        Object o=c.newInstance();
        handlePostConstruct(o,c);
        String beanId=c.getSimpleName().substring(0,1).toLowerCase()+c.getSimpleName().substring(1);
        beanMap.put(beanId,o);
    }

    private void scanPackageAndSubPackageClasses(String basePackage) throws IOException, ClassNotFoundException {
        String packagePath=basePackage.replaceAll("\\.","/");
        System.out.println("扫描包路径:"+basePackage+",替换后:"+packagePath);//com.yc.bean->com/yc.bean
        Enumeration<URL> files=Thread.currentThread().getContextClassLoader().getResources(packagePath);
        while (files.hasMoreElements()){
            URL url=files.nextElement();
            System.out.println("配置的扫描路径为:"+url.getFile());
            //TODO:递归这些目录，查找 .class文件
            findClassesInPackages(url.getFile(),basePackage);
        }
    }

    private Set<Class> managedBeanClasses=new HashSet<Class>();

    /**
     * 查找 file 下面及子包所有的要托管的class，存到一个Set(managedBeanClasses)中
     * @param file
     * @param basePackage
     */
    private void findClassesInPackages(String file, String basePackage) throws ClassNotFoundException {
        File f=new File(file);
        File[] classFiles=f.listFiles(new FileFilter() {
            public boolean accept(File file) {
                return file.getName().endsWith(".class")|| file.isDirectory();
            }
        });

        //System.out.println(classFiles);

        for(File cf:classFiles){
            if(cf.isDirectory()){
                basePackage+="."+cf.getName().substring(cf.getName().lastIndexOf("/")+1);
                findClassesInPackages(cf.getAbsolutePath(),basePackage);
            }else{
                //加载cf 作为class文件
                URL[] urls=new URL[]{};
                URLClassLoader ucl=new URLClassLoader(urls);
                //com.yc.bean.Hello.class->com.yc.bean.Hello
                Class c=ucl.loadClass(basePackage+"."+cf.getName().replace(".class",""));
                managedBeanClasses.add(c);
            }
        }

    }


    /**
     * 处理MyAppConfig配置类中的Bean注解，完成操IOC作
     * @param cls
     * @param obj
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    private void handleAtMyBean(Class cls,Object obj) throws InvocationTargetException, IllegalAccessException {
        Method[] ms=cls.getDeclaredMethods();

        for(Method m:ms){
            if(m.isAnnotationPresent(MyBean.class)){
                Object o=m.invoke(obj);
                handlePostConstruct(o,o.getClass());
                beanMap.put(m.getName(),o);
            }
        }
    }

    /**
     * 处理一个Bean中的 @MyPostConstruct对应的方法
     * @param o
     * @param cls
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    private void handlePostConstruct(Object o, Class<?> cls) throws InvocationTargetException, IllegalAccessException {
        Method[] ms=cls.getDeclaredMethods();
        for(Method m:ms){
            if(m.isAnnotationPresent(MyPostConstruct.class)){
                m.invoke(o);
            }
        }
    }

    /**
     * 获取当前AppConfig类所在的包路径
     * @param cl
     * @return
     */
    private String[] getAppConfigBasePackages(Class cl){
        String[] paths=new String[1];
        paths[0]=cl.getPackage().getName();
        return paths;
    }


    public Object getBean(String id) {
        return beanMap.get(id);
    }
}
