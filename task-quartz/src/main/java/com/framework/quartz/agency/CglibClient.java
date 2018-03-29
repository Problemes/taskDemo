package com.framework.quartz.agency;

/**
 * Created by HR on 2017/12/6.
 */
public class CglibClient {

    public static void main(String[] args) {

        CglibProxy proxy = CglibProxy.newInstance();

        CglibEntity entity = proxy.getProxy(CglibEntity.class);

        entity.say();
    }

}
