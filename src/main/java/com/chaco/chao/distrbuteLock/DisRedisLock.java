package com.chaco.chao.distrbuteLock;

import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;

/**
 * wiki:
 * https://blog.csdn.net/tianyaleixiaowu/article/details/96112684
 * https://www.cnblogs.com/qdhxhz/p/11046905.html
 * author:zhaopeiyan001
 * Date:2020-04-14 14:42
 */
public class RedisLock {

    public void getLock() {
        RedissonClient redissonClient = Redisson.create();
        RLock key = redissonClient.getLock("key");

    }
}
