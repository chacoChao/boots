package com.chaco.chao.design.pattern.responsechain;

/**
 * author:zhaopeiyan001
 * Date:2019-08-09 18:45
 */
public class LeaveHandlerFactory {

    public static LeaveHandler createHandler() {
        LeaveHandler lead = new Lead();
        LeaveHandler cto = new CTO();
        LeaveHandler hrBoss = new HrBoss();
        LeaveHandler ceo = new CEO();

        lead.setSuccessor(cto);
        cto.setSuccessor(hrBoss);
        hrBoss.setSuccessor(ceo);
        return lead;
    }
}
