package com.chaco.chao.config.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class RedisLock {

    public interface LockRunner<T> {
        T execute();
    }
    private static final String APP_NAME = "_";
    private static final long REDIS_LOCK_EXPIRE = 5 * 60 * 1000L;// 5 * 60s 占锁最大时间
    private static final String LOCK_PREFIX = "LOCK_PREFIX_";
    private static final long TIMEOUT = 3 * 1000;//TRY LOCK 获取锁的超时时间
    private static final long RETRY_TIME = 100;
    private static final Logger LOGGER = LoggerFactory.getLogger(RedisLock.class);

    @Resource
    private RedisLockSource redisLockSource;

    public <R> R tryLock(String key, LockRunner<R> biz) throws RedisLockException {
        long startTime = System.currentTimeMillis();
        boolean gotLock = false;//是否获取到锁
        String lockKey = LOCK_PREFIX + APP_NAME + key;

        long lockStartTime = 0;
        while (true) {
            if (System.currentTimeMillis() - startTime > TIMEOUT) {
                LOGGER.warn("redis add lock fail ; key = {} please try later", key);
                break;
            }
            lockStartTime = System.currentTimeMillis();
            Long setNxRt = redisLockSource.setNx(lockKey, lockStartTime + REDIS_LOCK_EXPIRE + 1 + "");
            LOGGER.info("redisUtils.setNx(key={},value={}),result={}", lockKey, lockStartTime + REDIS_LOCK_EXPIRE + 1 + "", setNxRt);
            if (setNxRt.equals(1L)) { //加锁成功
                gotLock = true;
                break;
            } else {  //加锁失败
                String lockTimeStr = redisLockSource.get(lockKey);
                LOGGER.info("redisUtils.get(key={}),result={},curTime={}", lockKey, lockTimeStr, System.currentTimeMillis());

                if (lockTimeStr != null && Long.valueOf(lockTimeStr).longValue() < System.currentTimeMillis()) {//超时
                    String lockTimeStrCheck = redisLockSource.getSet(lockKey, lockStartTime + REDIS_LOCK_EXPIRE + 1 + "");
                    LOGGER.info("redisUtils.getSet(key={},value={}),result={}", lockKey, lockStartTime + REDIS_LOCK_EXPIRE + 1 + "", lockTimeStrCheck);

                    if (lockTimeStrCheck.equals(lockTimeStr)) {
                        gotLock = true;
                        break;
                    }
                }
                try {
                    Thread.sleep(RETRY_TIME);
                } catch (InterruptedException e) {
                    LOGGER.error("Redis Lock InterruptedException", e);
                }
            }
        }
        if (gotLock) {//获取到锁
            LOGGER.info("获取锁成功,key={}", lockKey);
            try {
                return biz.execute();
            } catch (Exception e) {
                LOGGER.error("doBiz业务异常", e);
                throw e;
            } finally {
                if (System.currentTimeMillis() - lockStartTime < REDIS_LOCK_EXPIRE) {//执行时间小于过期时间
                    redisLockSource.delete(lockKey);
                    LOGGER.info("释放锁成功,key={}", lockKey);
                } else {
                    LOGGER.warn("占锁时间超过五分钟,key=" + lockKey);
                }
            }
        } else {//3秒内未获取到锁,视为超时
            throw new RedisLockException("获取锁" + lockKey + "超时,锁被其它进程占用");
        }
    }

    public static class RedisLockException extends RuntimeException {
        public RedisLockException(String msg) {
            super(msg);
        }

        public RedisLockException(String msg, Exception e) {
            super(msg, e);
        }
    }
}

