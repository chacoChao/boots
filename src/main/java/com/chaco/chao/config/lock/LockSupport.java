//package com.chaco.chao.config.lock;
//
//import com.chaco.chao.tools.finance.common.lock.RedisLock;
//import lombok.extern.slf4j.Slf4j;
//
//import javax.annotation.Resource;
//
///**
// * @Author: huangshuren
// * @Copyright (c) 2015, lianjia.com All Rights Reserved
// */
//@Slf4j
//public class LockSupport {
//    private static final String COLON_SEPARATOR = "_";
//    private static final String PERF_SUBJECT_LOCK_PERFIX = "chaco" + "_SUBJECT";
//
//    @Resource
//    private RedisLock redisLock;
//
//    public <T> T addRedisLock(String orderId, RedisLock.LockRunner<T> runner) {
//        String key = PERF_SUBJECT_LOCK_PERFIX + COLON_SEPARATOR + orderId + COLON_SEPARATOR;
//        return redisLock.tryLock(key, runner);
//    }
//}
