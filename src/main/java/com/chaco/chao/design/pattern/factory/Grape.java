package com.chaco.chao.design.pattern.factory;

/**
 * author:zhaopeiyan001
 * Date:2019-08-27 21:57
 */
public class Grape implements Fruit {

    private boolean seedless;

    @Override
    public void grow() {
        log("grape is growing");
    }

    @Override
    public void harvest() {
        log("grape has been harvested");
    }

    @Override
    public void plant() {
        log("grape has been planted");
    }

    /**
     * 辅助方法
     *
     * @param msg
     */
    public static void log(String msg) {
        System.out.println("grape:" + msg);
    }
}
