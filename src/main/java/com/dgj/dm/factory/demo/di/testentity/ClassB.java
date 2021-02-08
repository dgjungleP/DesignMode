package com.dgj.dm.factory.demo.di.testentity;

/**
 * @version: v1.0
 * @date: 2021/2/8
 * @author: dgj
 */
public class ClassB {
    private String host;
    private int port;

    public ClassB() {
    }

    public ClassB(String host, Integer port) {
        this.host = host;
        this.port = port;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
