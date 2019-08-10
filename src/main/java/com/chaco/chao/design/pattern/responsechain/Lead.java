package com.chaco.chao.design.pattern.responsechain;

/**
 * author:zhaopeiyan001
 * Date:2019-08-09 18:29
 */
public class Lead extends LeaveHandler {
    @Override
    public void disposeLeave(int day) {
        if (day <= 1) {
            System.out.println("i am robin, i can handle " + day + " holiday!");
        } else {
            System.out.println("i am robin, i can not handle " + day + " holiday!");
            //如果处理不了  像上级传递请求
            successor.disposeLeave(day);
        }
    }
}
