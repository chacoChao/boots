package com.chaco.chao.design.pattern.strategy.discount;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * author:zhaopeiyan001
 * Date:2019-08-23 18:09
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FlatRateStrategy extends DiscountStrategy {

    private double price = 0;
    private int copies = 0;
    private double amount;

    @Override
    public double calculateDiscount() {
        return copies * amount;
    }
}
