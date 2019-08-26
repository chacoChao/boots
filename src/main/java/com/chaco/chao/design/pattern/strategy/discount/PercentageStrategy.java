package com.chaco.chao.design.pattern.strategy.discount;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * author:zhaopeiyan001
 * Date:2019-08-23 18:16
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PercentageStrategy extends DiscountStrategy {
    private double price = 0;
    private int copies = 0;
    private double percent;

    @Override
    public double calculateDiscount() {
        return copies * price * percent;
    }
}
