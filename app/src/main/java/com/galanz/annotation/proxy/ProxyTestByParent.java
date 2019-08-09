package com.galanz.annotation.proxy;

public class ProxyTestByParent extends Test {
    @Override
    public void test() {
        System.out.println("ProxyTestByParent---------前置内容");
       super.test();
        System.out.println("ProxyTestByParent---------后置内容");
    }
}
