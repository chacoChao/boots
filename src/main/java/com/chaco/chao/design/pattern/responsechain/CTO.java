package com.chaco.chao.design.pattern.responsechain;

/**
 * author:zhaopeiyan001
 * Date:2019-08-09 18:35
 */
public class CTO extends LeaveHandler {
    @Override
    public void disposeLeave(int day) {
        if (day <= 3) {
            System.out.println("i am CTO, i can handle " + day + " holiday.");
        } else {
            System.out.println("i am CTO, i can not handle " + day + " holiday.");
            successor.disposeLeave(day);
        }
    }
}
