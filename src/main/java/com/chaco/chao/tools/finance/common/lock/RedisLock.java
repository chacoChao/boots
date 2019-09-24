package com.chaco.chao.tools.finance.common.lock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by dongmingbin on 17/6/19.
 */
@Service
public class RedisLock<R> {

    @Resource
    RedisUtils redisUtils;

    private final long REDIS_LOCK_EXPIRE = 60 * 1000L;//60s 占锁最大时间
    private final String LOCK_PERFIX = "LOCK_PERFIX_PAY_";
    private final long TIMEOUT = 3 * 1000;//TRY LOCK 获取锁的超时时间
    private final long RETRY_TIME = 100;

    private Logger logger = LoggerFactory.getLogger(RedisLock.class);

    public R tryLock(String key, ILockBiz<R> biz) throws RedisLockException {
        long inTime = System.currentTimeMillis();
        boolean gotLock = false;//是否获取到锁
        String lockKey = LOCK_PERFIX + key;

        long lockStartTime = 0;
        while (true) {
            if (System.currentTimeMillis() - inTime > TIMEOUT) {
                break;
            }
            lockStartTime = System.currentTimeMillis();
            Long setNxRt = redisUtils.setNx(lockKey, lockStartTime + REDIS_LOCK_EXPIRE + 1 + "");
            logger.debug("redisUtils.setNx(key={},value={}),result={}", lockKey, lockStartTime + REDIS_LOCK_EXPIRE + 1 + "", setNxRt);
            if (setNxRt.equals(1L)) {
                gotLock = true;
                break;
            } else {
                String lockTimeStr = redisUtils.get(lockKey);
                logger.debug("redisUtils.get(key={}),result={},curTime={}", lockKey, lockTimeStr, System.currentTimeMillis());

                if (lockTimeStr != null && Long.valueOf(lockTimeStr).longValue() < System.currentTimeMillis()) {//超时
                    String lockTimeStrCheck = redisUtils.getSet(lockKey, lockStartTime + REDIS_LOCK_EXPIRE + 1 + "");
                    logger.debug("redisUtils.getSet(key={},value={}),result={}", lockKey, lockStartTime + REDIS_LOCK_EXPIRE + 1 + "", lockTimeStrCheck);

                    if (lockTimeStrCheck.equals(lockTimeStr)) {
                        gotLock = true;
                        break;
                    }
                }
                try {
                    Thread.sleep(RETRY_TIME);
                } catch (InterruptedException e) {
                    logger.error("",e);
                }
            }
        }
        if (gotLock) {//获取到锁
            logger.debug("获取锁成功,key={}", lockKey);
            try {
                return biz.doBiz();
            } catch (Exception e) {
                logger.error("doBiz业务异常", e);
                throw e;
            } finally {
                if (System.currentTimeMillis() - lockStartTime < REDIS_LOCK_EXPIRE) {//执行时间小于60s
                    redisUtils.delete(lockKey);
                    logger.debug("释放锁成功,key={}", lockKey);
                } else {
                    logger.warn("占锁时间超过10s,key=" + lockKey);
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


