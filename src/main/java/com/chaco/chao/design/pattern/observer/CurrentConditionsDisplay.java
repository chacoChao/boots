package com.chaco.chao.design.pattern.observer;

import org.springframework.stereotype.Component;

/**
 * author:zhaopeiyan001
 * Date:2019-08-15 14:10
 */
@Component
public class CurrentConditionsDisplay implements Observer, DisplayElement {

    private WeaterData weaterData;

    private float temperature;
    private float humidity;
    private float pressure;

    public CurrentConditionsDisplay(WeaterData weaterData) {
        this.weaterData = weaterData;
        this.weaterData.registerObserver(this);
    }

    @Override
    public void display() {
        System.out.println("当前温度为：" + this.temperature + "C");
        System.out.println("当前湿度为：" + this.humidity);
        System.out.println("当前气压为：" + this.pressure);
    }

    @Override
    public void update(String newState) {

    }

    @Override
    public void update() {
        this.temperature = this.weaterData.getTemperature();
        this.humidity = this.weaterData.getHumidity();
        this.pressure = this.weaterData.getPressure();
        display();
    }
}
