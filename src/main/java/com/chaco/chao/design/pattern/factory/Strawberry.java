package com.chaco.chao.design.pattern.factory;

import lombok.Data;

/**
 * author:zhaopeiyan001
 * Date:2019-08-28 16:21
 */
@Data
public class Strawberry implements Fruit {

    @Override
    public void grow() {
        log("strawbeery is growing");
    }

    @Override
    public void harvest() {
        log("strawberry has been harvested");
    }

    @Override
    public void plant() {
        log("starwberry has been planted");
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
