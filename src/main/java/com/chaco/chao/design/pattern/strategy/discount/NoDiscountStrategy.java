package com.chaco.chao.design.pattern.strategy.discount;

import lombok.Data;

/**
 * author:zhaopeiyan001
 * Date:2019-08-23 18:02
 */
@Data
public class NoDiscountStrategy extends DiscountStrategy {

    private double price = 0D;

    private int copies = 0;

    @Override
    public double calculateDiscount() {
        return 0;
    }

    public NoDiscountStrategy(double price, int copies) {
        this.copies = copies;
        this.price = price;
    }
}
