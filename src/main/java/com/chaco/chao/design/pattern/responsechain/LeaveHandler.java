package com.chaco.chao.design.pattern.responsechain;

/**
 * responseChain 责任链设计模式
 * author:zhaopeiyan001
 * Date:2019-08-09 18:24
 */
public abstract class LeaveHandler {

    protected LeaveHandler successor;

    public void setSuccessor(LeaveHandler leaveHandler) {
        this.successor = leaveHandler;
    }

    public abstract void disposeLeave(int day);
}
