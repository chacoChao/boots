package com.chaco.chao.design.pattern.factory;

import java.text.DateFormat;

/**
 * author:zhaopeiyan001
 * Date:2019-08-29 15:35
 */
public class ConcreteProduct implements Product {

    public ConcreteProduct() {

    }

    /**
     * 静态工厂方法
     * @return
     */
    public static ConcreteProduct factory() {
        DateFormat dateInstance = DateFormat.getDateInstance();
        DateFormat.getInstance();
        return new ConcreteProduct();
    }
}
