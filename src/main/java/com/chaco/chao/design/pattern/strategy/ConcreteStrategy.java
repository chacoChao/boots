package com.chaco.chao.design.pattern.strategy;

import java.awt.*;

/**
 * author:zhaopeiyan001
 * Date:2019-08-23 16:11
 */
public class ConcreteStrategy extends Strategy {
    /**
     * 策略方法
     */
    @Override
    public void strategyInterface() {
        // write algorithm code
        new LayoutManager() {
            @Override
            public void addLayoutComponent(String name, Component comp) {

            }

            @Override
            public void removeLayoutComponent(Component comp) {

            }

            @Override
            public Dimension preferredLayoutSize(Container parent) {
                return null;
            }

            @Override
            public Dimension minimumLayoutSize(Container parent) {
                return null;
            }

            @Override
            public void layoutContainer(Container parent) {

            }
        };
    }
}
