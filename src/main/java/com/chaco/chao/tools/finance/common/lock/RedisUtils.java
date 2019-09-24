package com.chaco.chao.tools.finance.common.lock;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Redis工具类,用于数据库表数据的缓存
 * User: shipengzhi
 * Date: 14-2-19
 * Time: 上午11:31
 */
public class RedisUtils {

    private String host ;
    private int port ;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public int getDataBase() {

        return dataBase;
    }

    public void setDataBase(int dataBase) {
        this.dataBase = dataBase;
    }

    public int getExpire() {
        return expire;
    }

    public void setExpire(int expire) {
        this.expire = expire;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Long delete(final String key) {
        Jedis jedis = jedisPool.getResource();
        try {
            return jedis.del(key);
        } finally {
            jedis.close();
        }
    }


    public String getSet(final String key, final String value) {
        Jedis jedis = jedisPool.getResource();
        try {
            return jedis.getSet(key, value);
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
            return jedis.setnx(key, value);
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
            return jedis.get(key);
        } finally {
            jedis.close();
        }
    }

    /**
     * 设置key的值，如果存在就覆盖
     *
     * @param key
     * @param value
     * @return
     */
    public boolean set(String key, String value) {
        Jedis jedis = jedisPool.getResource();
        try {
            return "OK".equals(jedis.set(key, value));
        } finally {
            jedis.close();
        }
    }


    /**
     * #如果指定的Key不存在，则设定该Key持有指定字符串Value，此时其效果等价于SET命令。
     * 相反，如果该Key已经存在，该命令将不做任何操作并返回。
     *
     *
     * @param key
     * @param value
     * @param seconds 过期时间（秒）
     * @return
     */
    public boolean setex(String key, String value, int seconds) {
        Jedis jedis = jedisPool.getResource();
        try {
            return "OK".equals(jedis.setex(key, seconds, value));
        } finally {
            jedis.close();
        }
    }

    /**
     * @Author zhaopeiyan001
     * @Description 判断key是否存在
     * @Date  2018/10/29
     * @Param
     * @return
     **/
    public boolean exist(String key) {
        Jedis jedis = jedisPool.getResource();
        try {
            return (jedis.exists(key));
        } finally {
            jedis.close();
        }
    }

}

