package com.dgj.dm.factory.demo.di.parser;

import com.dgj.dm.factory.demo.di.core.bean.BeanDefinion;

import java.io.InputStream;
import java.util.List;

/**
 * @version: v1.0
 * @date: 2021/2/7
 * @author: dgj
 */
public interface BeanConfigParser {

    List<BeanDefinion> parse(InputStream inputStream);

    List<BeanDefinion> parse(String configContent);
}
