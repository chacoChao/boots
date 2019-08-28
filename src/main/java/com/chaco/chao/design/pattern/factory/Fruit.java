package com.chaco.chao.design.pattern.factory;

import lombok.Data;

/**
 * author:zhaopeiyan001
 * Date:2019-08-27 21:51
 */
public interface Fruit {
    /**
     * 生长
     */
    void grow();

    /**
     * 收获
     */
    void harvest();

    /**
     * 种植
     */
    void plant();

}
