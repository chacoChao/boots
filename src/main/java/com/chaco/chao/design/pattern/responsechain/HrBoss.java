package com.chaco.chao.design.pattern.responsechain;

/**
 * author:zhaopeiyan001
 * Date:2019-08-09 18:37
 */
public class HrBoss extends LeaveHandler {
    @Override
    public void disposeLeave(int day) {
        if (day <= 5) {
            System.out.println("i am hrBoss, i can handle " + day + " holiday");
        } else {
            System.out.println("i am hrBoss, i can not handle " + day + " holiday");
            successor.disposeLeave(day);
        }
    }
}
