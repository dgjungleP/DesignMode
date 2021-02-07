package com.dgj.dm.factory.demo.di.core.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * 解析配置后获得的Bean的标准格式
 *
 * @version: v1.0
 * @date: 2021/2/7
 * @author: dgj
 */
public class BeanDefinion {
    private String id;
    private String className;
    private List<ConstructorArg> constructorArgs = new ArrayList<>();
    private Scope scope = Scope.SINGLETON;
    private boolean lazyInit = false;

    public boolean isSingleton() {
        return Scope.SINGLETON.equals(scope);
    }

    public enum Scope {
        SINGLETON, PROTOTYPE
    }

    public static class ConstructorArg {
        private boolean isRef;
        private Class type;
        private Object arg;
    }
}
