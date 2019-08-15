package com.chaco.chao.design.pattern.observer;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;

/**
 * author:zhaopeiyan001
 * Date:2019-08-15 14:27
 */
@SpringBootTest
@Slf4j
public class ObserverTest {

    @Test
    public void observerT() {
        WeaterData weaterData = new WeaterData();
        CurrentConditionsDisplay currentConditionsDisplay = new CurrentConditionsDisplay(weaterData);
        ForecastDisplay forecastDisplay = new ForecastDisplay(weaterData);
        ArrayList<Float> floats = new ArrayList<>();
        floats.add(22f);
        floats.add(-1f);
        floats.add(9f);
        floats.add(23f);
        floats.add(27f);
        floats.add(30f);
        floats.add(10f);
        weaterData.setMeasurements(22f, 0.8f, 1.2f, floats);
    }

    @Test
    public void aTest() {
        System.out.println("atest====");
        System.out.println("atest====");
        System.out.println("atest====");
        System.out.println("atest====");
        System.out.println("atest====");
        System.out.println("atest====");

    }
}
