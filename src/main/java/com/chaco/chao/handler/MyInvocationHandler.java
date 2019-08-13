package com.chaco.chao.handler;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * author:zhaopeiyan001
 * Date:2019-08-13 10:50
 */
public class MyInvocationHandler implements InvocationHandler {

    /**
     * target object
     */
    private Object target;

    public MyInvocationHandler(Object target) {
        super();
        this.target = target;
    }

    /**
     * execute target method
     *
     * @param proxy
     * @param method
     * @param args
     * @return
     * @throws Throwable
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("--------------------before-----------------------");

        Object result = method.invoke(target, args);

        System.out.println("--------------------after-----------------------");
        return result;
    }

    /**
     * get target object proxy instance
     * @return
     */
    public Object getProxy() {
        return Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), target.getClass().getInterfaces(), this);
    }
}
