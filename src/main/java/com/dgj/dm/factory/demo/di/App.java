package com.dgj.dm.factory.demo.di;

import com.dgj.dm.factory.demo.di.context.ClassPathXmlApplicationContext;

/**
 * @version: v1.0
 * @date: 2021/2/5
 * @author: dgj
 */
public class App {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("beans_factory.xml");
        Object hello = context.getBean("redisCounter");
    }
}
