package com.chaco.chao.config.redis;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;

/**
 * @author caoyixiong
 * @Date: 2018/10/11
 * @Copyright (c) 2015, lianjia.com All Rights Reserved
 */
@SpringBootConfiguration
public class RedisBean {

    @Value("${redis.host}")
    private String redisHost;
    @Value("${redis.port}")
    private int redisPort;
    @Value("${redis.password}")
    private String redisPassword;
    @Value("${redis.database}")
    private int redisDataBase;
    @Value("${redis.expire}")
    private int redisExpire;
    @Value("${redis.timeout}")
    private int redisTimeout;

    @Bean
    public RedisLockSource createRedisLockSource() {
        RedisLockSource redisLockSource = new RedisLockSource();
        redisLockSource.setHost(redisHost);
        redisLockSource.setPort(redisPort);
        redisLockSource.setDataBase(redisDataBase);
        redisLockSource.setExpire(redisExpire);
        redisLockSource.setTimeout(redisTimeout);
        redisLockSource.setPassword(redisPassword);

        redisLockSource.init();
        return redisLockSource;
    }
}
