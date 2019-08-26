package com.chaco.chao.design.pattern.strategy.discount;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * author:zhaopeiyan001
 * Date:2019-08-23 17:24
 */
@Data
@NoArgsConstructor
public abstract class DiscountStrategy {
    private double price = 0;
    private int copies = 0;

    /**
     * strategy method
     * @return
     */
    public abstract double calculateDiscount();

    public DiscountStrategy(double price, int copies) {
        this.price = price;
        this.copies = copies;
    }
}
