package com.chaco.chao.design.pattern.responsechain;

/**
 * author:zhaopeiyan001
 * Date:2019-08-09 18:38
 */
public class CEO extends LeaveHandler {
    @Override
    public void disposeLeave(int day) {
        System.out.println("i am CEO, i can handle " + day + " holiday");
    }
}
