package com.dgj.dm.factory.demo.di.factory;

import com.dgj.dm.factory.demo.di.core.bean.BeanDefinion;
import com.dgj.dm.factory.demo.di.exception.BeanCreationFailureException;
import com.dgj.dm.factory.demo.di.exception.NoSuchBeanDefinionException;
import com.google.common.annotations.VisibleForTesting;

import java.lang.reflect.InvocationTargetException;
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
     * 以懒加载的形式获取对应的bean对象
     *
     * @param beanName
     * @return
     */
    public Object getBean(String beanName) {
        BeanDefinion definion = beanDefinions.get(beanName);
        if (definion == null) {
            throw new NoSuchBeanDefinionException("Bean is not defined: " + beanName);
        }
        return createBean(definion);
    }


    /**
     * @param beanDefinions
     */
    public void addBeanDefinion(List<BeanDefinion> beanDefinions) {
        for (BeanDefinion definion : beanDefinions) {
            this.beanDefinions.putIfAbsent(definion.getId(), definion);
        }
        for (BeanDefinion definion : beanDefinions) {
            if (definion.isLazyInit() == false && definion.isSingleton()) {
                createBean(definion);
            }
        }
    }

    @VisibleForTesting
    protected Object createBean(BeanDefinion beanDefinion) {
        if (beanDefinion.isSingleton() && singletonObjects.containsKey(beanDefinion.getId())) {
            return singletonObjects.get(beanDefinion.getId());
        }
        Object bean;
        try {
            Class<?> beanClass = Class.forName(beanDefinion.getClassName());
            List<BeanDefinion.ConstructorArg> constructorArgs = beanDefinion.getConstructorArgs();
            if (constructorArgs.isEmpty()) {
                bean = beanClass.newInstance();
            } else {
                int argSize = constructorArgs.size();
                Class[] argClasses = new Class[argSize];
                Object[] argObjects = new Object[argSize];
                for (int i = 0; i < argSize; i++) {
                    BeanDefinion.ConstructorArg arg = constructorArgs.get(i);
                    if (arg.isRef()) {
                        BeanDefinion definion = beanDefinions.get(arg.getArg());
                        if (definion == null) {
                            throw new NoSuchBeanDefinionException("Bean is not defined: " + arg.getArg());
                        }
                        argClasses[i] = Class.forName(definion.getClassName());
                        argObjects[i] = createBean(definion);
                    } else {
                        argClasses[i] = arg.getType();
                        argObjects[i] = arg.getArg();
                    }
                }
                bean = beanClass.getConstructor(argClasses).newInstance(argObjects);
            }
        } catch (ClassNotFoundException | IllegalAccessException
                | InstantiationException | NoSuchMethodException | InvocationTargetException e) {
            throw new BeanCreationFailureException("", e);
        }
        if (bean != null && beanDefinion.isSingleton()) {
            singletonObjects.putIfAbsent(beanDefinion.getId(), bean);
            return singletonObjects.get(beanDefinion.getId());
        }
        return bean;
    }
}
