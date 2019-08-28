package com.chaco.chao.design.pattern.factory;

import lombok.Data;

/**
 * author:zhaopeiyan001
 * Date:2019-08-27 21:52
 */
@Data
public class Apple implements Fruit {

    private int treeAge;

    @Override
    public void grow() {
        log("Apple is growing");
    }

    @Override
    public void harvest() {
        log("Apple has been harvested");
    }

    @Override
    public void plant() {
        log("Apple has panted");
    }

    /**
     * 辅助方法
     *
     * @param msg
     */
    public static void log(String msg) {
        System.out.println("Apple:" + msg);
    }
}
