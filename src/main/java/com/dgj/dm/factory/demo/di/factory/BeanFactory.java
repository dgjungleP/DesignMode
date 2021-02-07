package com.dgj.dm.factory.demo.di.factory;

import com.dgj.dm.factory.demo.di.core.bean.BeanDefinion;
import com.google.common.annotations.VisibleForTesting;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @version: v1.0
 * @date: 2021/2/7
 * @author: dgj
 */
public class BeanFactory {
    private ConcurrentHashMap<String, Object> singletonObjects = new ConcurrentHashMap<>();
    private ConcurrentHashMap<String, BeanDefinion> beanDefinions = new ConcurrentHashMap<>();


    /**
     * @param beanName
     * @return
     */
    public Object getBean(String beanName) {
        return null;
    }


    /**
     * @param beanDefinions
     */
    public void addBeanDefinion(List<BeanDefinion> beanDefinions) {
    }

    @VisibleForTesting
    protected Object createBean(BeanDefinion beanDefinion) {
        return null;
    }
}
