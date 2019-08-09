package com.galanz.annotation.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class ProxyMain {


    public static void main(String arg[]){

        System.out.println("-------------------静态代理 by parent---------------");
        ProxyTestByParent proxyTestByParent = new ProxyTestByParent();
        proxyTestByParent.test();

        System.out.println("-------------------静态代理 by interface-------------");
        ProxyTestByInterface proxyTestByInterface = new ProxyTestByInterface(new Test());
        proxyTestByInterface.test();

        System.out.println("-------------------jdk动态代理--------------------------");
        final ITest user = new Test();
        ITest proxy = (ITest) Proxy.newProxyInstance(ITest.class.getClassLoader(), new Class[]{ITest.class}, new InvocationHandler() {
            @Override
            public Object invoke(Object o, Method method, Object[] objects) throws Throwable {
                System.out.println("前置内容");
                method.invoke(user, objects);
                System.out.println("后置内容");
                return null;
            }
        });
        proxy.test();

        System.out.println("-------------------cglib动态代理--------------------------");


        Test pr = (Test) new TestCglib().getInstance(user);
        pr.test();
    }
}
