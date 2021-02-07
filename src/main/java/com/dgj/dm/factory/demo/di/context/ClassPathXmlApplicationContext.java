package com.dgj.dm.factory.demo.di.context;

import com.dgj.dm.factory.demo.di.factory.BeanFactory;
import com.dgj.dm.factory.demo.di.parser.BeanConfigParser;
import com.dgj.dm.factory.demo.di.parser.XmlBeanConfigParser;

/**
 * @version: v1.0
 * @date: 2021/2/7
 * @author: dgj
 */
public class ClassPathXmlApplicationContext implements ApplicationContext {
    private BeanFactory beanFactory;
    private BeanConfigParser beanConfigParser;

    public ClassPathXmlApplicationContext(String configurations) {
        beanFactory = new BeanFactory();
        beanConfigParser = new XmlBeanConfigParser();
        loadBeanLocation(configurations);
    }

    /**
     * 根据文件内容解析bean对象信息
     *
     * @param configurations
     */
    private void loadBeanLocation(String configurations) {

    }


    public Object getBean(String beanName) {
        return beanFactory.getBean(beanName);
    }
}
