package com.chaco.chao.design.pattern.strategy;

/**
 * author:zhaopeiyan001
 * Date:2019-08-23 16:09
 */
public class Context {
    private Strategy strategy;

    /**
     * 策略方法
     */
    public void contextInterface() {
        strategy.strategyInterface();
    }
}
