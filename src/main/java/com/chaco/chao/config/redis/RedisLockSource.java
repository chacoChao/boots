package com.chaco.chao.config.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisLockSource {

    private static final String PREFIX = "project_harden_";
    private String host;
    private int port;
    private int timeout;
    private int expire;
    private String password;
    private int dataBase;
    private static JedisPool jedisPool = null;


    public void init() {
        if (jedisPool == null) {
            if (this.password != null && "".equals(this.password.trim())) {
                this.password = null;
            }

            jedisPool = new JedisPool(new JedisPoolConfig(), this.host, this.port, this.timeout, this.password, this.dataBase);
        }

    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public int getExpire() {
        return expire;
    }

    public void setExpire(int expire) {
        this.expire = expire;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getDataBase() {
        return dataBase;
    }

    public void setDataBase(int dataBase) {
        this.dataBase = dataBase;
    }

    public Long delete(final String key) {
        Jedis jedis = jedisPool.getResource();
        try {
            return jedis.del(PREFIX + key);
        } finally {
            jedis.close();
        }
    }


    public String getSet(final String key, final String value) {
        Jedis jedis = jedisPool.getResource();
        try {
            return jedis.getSet(PREFIX + key, value);
        } finally {
            jedis.close();
        }
    }


    /**
     * 设置缓存内容
     * 冲突不覆盖
     */
    public Long setNx(final String key, final String value) {
        Jedis jedis = jedisPool.getResource();
        try {
            return jedis.setnx(PREFIX + key, value);
        } finally {
            jedis.close();
        }
    }

    /**
     * 根据key取缓存内容
     */
    public String get(final String key) {
        Jedis jedis = jedisPool.getResource();
        try {
            return jedis.get(PREFIX + key);
        } finally {
            jedis.close();
        }
    }
}