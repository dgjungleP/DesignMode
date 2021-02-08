package com.dgj.dm.factory.demo.di.util;

import java.util.HashMap;

/**
 * @version: v1.0
 * @date: 2021/2/8
 * @author: dgj
 */
public class ClassUtil {
    private static HashMap<String, Class> classMap = new HashMap<>();

    static {
        try {
            classMap.put("String", Class.forName("java.lang.String"));
            classMap.put("Double", Class.forName("java.lang.Double"));
            classMap.put("Integer", Class.forName("java.lang.Integer"));
            classMap.put("Float", Class.forName("java.lang.Float"));
            classMap.put("Long", Class.forName("java.lang.Long"));
            classMap.put("Short", Class.forName("java.lang.Short"));
            classMap.put("Byte", Class.forName("java.lang.Byte"));
            classMap.put("Boolean", Class.forName("java.lang.Boolean"));
            classMap.put("Character", Class.forName("java.lang.Character"));
            classMap.put("double", Class.forName("java.lang.Double"));
            classMap.put("int", Class.forName("java.lang.Integer"));
            classMap.put("float", Class.forName("java.lang.Float"));
            classMap.put("long", Class.forName("java.lang.Long"));
            classMap.put("short", Class.forName("java.lang.Short"));
            classMap.put("byte", Class.forName("java.lang.Byte"));
            classMap.put("boolean", Class.forName("java.lang.Boolean"));
            classMap.put("char", Class.forName("java.lang.Character"));

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static Class coverClass(String className) {
        return classMap.get(className);
    }
}
