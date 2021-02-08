package com.dgj.dm.factory.demo.di.testentity;

/**
 * @version: v1.0
 * @date: 2021/2/8
 * @author: dgj
 */
public class ClassA {
    private ClassB classB;

    public ClassA() {
    }

    public ClassA(ClassB classB) {
        this.classB = classB;
    }

    public ClassB getClassB() {
        return classB;
    }

    public void setClassB(ClassB classB) {
        this.classB = classB;
    }
}
