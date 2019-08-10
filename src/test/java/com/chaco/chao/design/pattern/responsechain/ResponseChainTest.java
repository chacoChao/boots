package com.chaco.chao.design.pattern.responsechain;

import org.junit.Assert;

import java.util.Date;

/**
 * author:zhaopeiyan001
 * Date:2019-08-09 18:40
 */
public class ResponseChainTest {

    private LeaveHandler leaveHandler;

    public void sethanle(LeaveHandler handler) {
        this.leaveHandler = handler;
    }

    public void requestDiscount(int day) {
        leaveHandler.disposeLeave(day);
    }

    public static void main(String[] args) {
        ResponseChainTest responseChainTest = new ResponseChainTest();
        responseChainTest.sethanle(LeaveHandlerFactory.createHandler());
        responseChainTest.requestDiscount(8);
        Assert.assertNotNull(new Date());
    }
}
