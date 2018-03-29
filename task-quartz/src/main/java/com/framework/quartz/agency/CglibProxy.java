package com.framework.quartz.agency;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;


/**
 * AOP:http://www.importnew.com/21807.html
 * cglib 解决了JDK动态代理需要实现接口的弊端
 * Created by HR on 2017/12/6.
 */
public class CglibProxy implements MethodInterceptor {

    /**
     * 静态内部类单例
     * @return
     */
    private CglibProxy(){}

    public static CglibProxy newInstance(){
        return SingletonHolder.cglibProxy;
    }

    private static class SingletonHolder{
       static CglibProxy cglibProxy = new CglibProxy();
    }


    private Object target;

    /**
     * 创建代理对象
//     * @param target
     * @return
     */
//    public Object getProxy(Object target) {
//        this.target = target;
//        Enhancer enhancer = new Enhancer();
//        enhancer.setSuperclass(this.target.getClass());
//        // 回调方法
//        enhancer.setCallback(this);
//        // 创建代理对象
//        return enhancer.create();
//    }
    public <T> T getProxy(Class<T> c){return (T) Enhancer.create(c, this);}


    @Override
    // 回调方法
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {

        System.out.println("method 开始:" + method.getName());
        Object object = proxy.invokeSuper(obj, args);
        System.out.println("method 结束:" + method.getName());
        return object;
    }

}
