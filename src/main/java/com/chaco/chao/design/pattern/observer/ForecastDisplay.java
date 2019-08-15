package com.chaco.chao.design.pattern.observer;

import org.springframework.stereotype.Component;

import java.util.List;

/**
 * author:zhaopeiyan001
 * Date:2019-08-15 14:18
 */
@Component
public class ForecastDisplay implements Observer, DisplayElement {

    private WeaterData weaterData;

    private List<Float> forecastTemperatures;

    public ForecastDisplay(WeaterData weaterData) {
        this.weaterData = weaterData;
        this.weaterData.registerObserver(this);
    }

    @Override
    public void display() {
        System.out.println("未来几天的温度");
        int size = forecastTemperatures.size();
        for (int i = 0; i < size; i++) {
            System.out.println("第" + i + "天：" + forecastTemperatures.get(i) + "C");
        }
    }

    @Override
    public void update(String newState) {

    }

    @Override
    public void update() {
        this.forecastTemperatures = this.weaterData.getForecastTemperatures();
        display();
    }
}
