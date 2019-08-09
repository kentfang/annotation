package com.galanz.annotation.proxy;

public class ProxyTestByInterface implements ITest {
    private ITest test;
    public ProxyTestByInterface(ITest test){
        this.test = test;
    }

    @Override
    public void test() {
        System.out.println("ProxyTestByInterface---------前置内容");
        test.test();
        System.out.println("ProxyTestByInterface---------后置内容");
    }
}
