package com.dgj.dm.factory.demo.di.context;

import com.dgj.dm.factory.demo.di.core.bean.BeanDefinion;
import com.dgj.dm.factory.demo.di.factory.BeanFactory;
import com.dgj.dm.factory.demo.di.parser.BeanConfigParser;
import com.dgj.dm.factory.demo.di.parser.XmlBeanConfigParser;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * @version: v1.0
 * @date: 2021/2/7
 * @author: dgj
 */
public class ClassPathXmlApplicationContext implements ApplicationContext {
    private BeanFactory beanFactory;
    private BeanConfigParser beanConfigParser;

    public ClassPathXmlApplicationContext(String location) {
        beanFactory = new BeanFactory();
        beanConfigParser = new XmlBeanConfigParser();
        loadBeanLocation(location);
    }

    /**
     * 根据文件内容解析bean对象信息
     *
     * @param location
     */
    private void loadBeanLocation(String location) {
        InputStream locationStream = null;
        try {
            locationStream = this.getClass().getClassLoader().getResourceAsStream(location);
            if (locationStream == null) {
                throw new RuntimeException("Can not found the config file : " + location);
            }
            List<BeanDefinion> beanDefinions = beanConfigParser.parse(locationStream);
            beanFactory.addBeanDefinion(beanDefinions);
        } finally {
            if (locationStream != null) {
                try {
                    locationStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }


    public Object getBean(String beanName) {
        return beanFactory.getBean(beanName);
    }
}
