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

    public BeanDefinion() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public List<ConstructorArg> getConstructorArgs() {
        return constructorArgs;
    }

    public void setConstructorArgs(List<ConstructorArg> constructorArgs) {
        this.constructorArgs = constructorArgs;
    }

    public Scope getScope() {
        return scope;
    }

    public void setScope(Scope scope) {
        this.scope = scope;
    }

    public boolean isLazyInit() {
        return lazyInit;
    }

    public void setLazyInit(boolean lazyInit) {
        this.lazyInit = lazyInit;
    }

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

        public ConstructorArg() {
        }

        public boolean isRef() {
            return isRef;
        }

        public void setRef(boolean ref) {
            isRef = ref;
        }

        public Class getType() {
            return type;
        }

        public void setType(Class type) {
            this.type = type;
        }

        public Object getArg() {
            return arg;
        }

        public void setArg(Object arg) {
            this.arg = arg;
        }
    }
}
