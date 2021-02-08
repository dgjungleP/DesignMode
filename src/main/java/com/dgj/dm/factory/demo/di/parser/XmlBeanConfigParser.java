package com.dgj.dm.factory.demo.di.parser;

import com.dgj.dm.factory.demo.di.core.bean.BeanDefinion;
import com.dgj.dm.factory.demo.di.util.ClassUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @version: v1.0
 * @date: 2021/2/7
 * @author: dgj
 */
public class XmlBeanConfigParser implements BeanConfigParser {

    public List<BeanDefinion> parse(InputStream inputStream) {
        StringBuffer content = new StringBuffer();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        try {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return parse(content.toString());
    }

    public List<BeanDefinion> parse(String configContent) {
        List<BeanDefinion> beanDefinionList;
        configContent = configContent.replaceAll(">( )+<", "><");
        Pattern pattern = Pattern.compile("^<beans>(<bean .*>.*</bean>)+</beans>$");
        Matcher matcher = pattern.matcher(configContent);
        if (!matcher.matches()) {
            throw new RuntimeException("Input a false config file, please check out!");
        }
        String group = matcher.group(1);
        String[] beanConfigs = group.split("</bean>");
        beanDefinionList = Arrays.stream(beanConfigs).map(this::cover).collect(Collectors.toList());
        return beanDefinionList;
    }

    private BeanDefinion cover(String beanConfig) {
        int index = beanConfig.indexOf(">");
        String bean = beanConfig.substring(0, index + 1);
        BeanDefinion definion = makeDefaultBean(bean);
        String args = beanConfig.substring(index + 1);
        List<BeanDefinion.ConstructorArg> argList = makeDefaultArg(args);
        definion.setConstructorArgs(argList);
        return definion;
    }

    private BeanDefinion makeDefaultBean(String bean) {
        BeanDefinion definion = new BeanDefinion();
        bean = bean.replaceAll("[<>]", "").substring(5).trim();
        String[] configs = bean.split(" ");
        Map<String, String> configMap = new HashMap<>();
        for (String config : configs) {
            String[] split = config.replace("\"", "").split("=");
            configMap.put(split[0], split[1]);
        }
        definion.setClassName(configMap.get("class"));
        definion.setId(configMap.get("id"));
        String lazyInit = configMap.get("lazy-init");
        if (lazyInit != null) {
            definion.setLazyInit(Boolean.parseBoolean(lazyInit));
        }
        String scope = configMap.get("scope");
        if (scope != null) {
            definion.setScope(BeanDefinion.Scope.valueOf(scope.toUpperCase()));
        }
        return definion;
    }

    private List<BeanDefinion.ConstructorArg> makeDefaultArg(String arg) {
        List<BeanDefinion.ConstructorArg> argList = new ArrayList<>();
        for (String argStr : arg.split("/>")) {
            argStr = argStr.substring(17).trim();
            argList.add(coverArgs(argStr));
        }
        return argList;
    }

    private BeanDefinion.ConstructorArg coverArgs(String constructorArgConfig) {
        HashMap<String, String> configMap = new HashMap<>();
        BeanDefinion.ConstructorArg arg = new BeanDefinion.ConstructorArg();
        for (String config : constructorArgConfig.split(" +")) {
            String[] split = config.replace("\"", "").split("=");
            configMap.put(split[0], split[1]);
        }
        String ref = configMap.get("ref");
        if (ref != null) {
            arg.setRef(true);
            arg.setType(String.class);
            arg.setArg(ref);
        } else {
            arg.setRef(false);
            Class type = ClassUtil.coverClass(configMap.get("type"));
            Object value;
            try {
                value = type.getConstructor(String.class).newInstance(configMap.get("value"));
                arg.setArg(type.cast(value));
                arg.setType(type);
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
        return arg;
    }
}
