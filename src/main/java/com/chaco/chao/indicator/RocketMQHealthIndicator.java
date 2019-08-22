package com.chaco.chao.indicator;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;

/**
 * author:zhaopeiyan001
 * Date:2019-08-22 14:00
 */
public class RocketMQHealthIndicator implements HealthIndicator {
    @Override
    public Health health() {
        int errCode = check();
        if (0 != errCode) {
            return Health.down().withDetail("ERROR CODE", errCode).build();
        }
        return Health.up().build();
    }

    private Integer check() {
        //
        return 1;
    }
}
