package com.chaco.chao.design.pattern.factory;

/**
 * author:zhaopeiyan001
 * Date:2019-08-29 15:34
 */
public class Creator {

    /**
     * 静态工厂方法
     * @return
     */
    public static Product factory() {
        return new ConcreteProduct();
    }
}
