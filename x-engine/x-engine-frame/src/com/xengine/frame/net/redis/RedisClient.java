package com.xengine.frame.net.redis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Protocol;

/**
 * redis 客户端包装器
 * @author 朱华煖
 */
@Slf4j
public abstract class RedisClient {
	
	//redis链接池
	protected JedisPool pool;
	
	/** 链接池的大小***/
	private final static int MAX_ACTIVE=1000;
	/** 获取链接池链接最大等待时间***/
	private final static int MAX_WAIT=1000;
    /*** 最大保持空闲状态的对象*/
    private final static int MAX_IDLE = 100;
    /*** 当调用borrow Object方法时，是否进行有效性检查*/
    private final static boolean TEST_ON_BORROW = true;
    /*** 当调用return Object方法时，是否进行有效性检查*/
    private final static boolean TEST_ON_RETURN = true;
    
	
    /**
     * 使用默认配置创建redis链接   (默认:user=root pwd='')
     * @param ip redis的ip地址
     * @param port redis的端口
     */
    public RedisClient(String ip, int port) {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(MAX_ACTIVE);
        config.setMaxIdle(MAX_IDLE);
        config.setMaxWaitMillis(MAX_WAIT);
        config.setTestOnBorrow(TEST_ON_BORROW);
        config.setTestOnReturn(TEST_ON_RETURN);
        pool = new JedisPool(config, ip, port);
    }
	
    /**
     * 创建redis链接 
     * @param ip redis的ip地址
     * @param port redis的端口
     * @param maxActive 链接池的大小
     * @param maxIdle 最大保持空闲状态的链接大小
     * @param maxWait 获取jedis链接最长等待时间 （ms 毫秒）
     */
    public RedisClient (String ip,int port,int maxActive,int maxIdle,int maxWait) {
    	 JedisPoolConfig config = new JedisPoolConfig();
         config.setMaxTotal(maxActive);
         config.setMaxIdle(maxIdle);
         config.setMaxWaitMillis(maxWait);
         config.setTestOnBorrow(TEST_ON_BORROW);
         config.setTestOnReturn(TEST_ON_RETURN);
         pool = new JedisPool(config, ip, port);
    }
    
    /**
     * 创建redis链接 
     * @param ip redis的ip地址
     * @param port redis的端口 
     * @param password redis的密码 （加密之后的密码）
     * @param maxActive redis链接池的大小
     * @param maxIdle 最大保持空闲状态的链接
     * @param maxWait 获取jedis的链接最大的等待时间 （ms 毫秒）
     */
    public RedisClient(String ip, int port, String password, int maxActive, int maxIdle, int maxWait) {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(maxActive);
        config.setMaxIdle(maxIdle);
        config.setMaxWaitMillis(maxWait);
        config.setTestOnBorrow(TEST_ON_BORROW);
        config.setTestOnReturn(TEST_ON_RETURN);
        pool = new JedisPool(config, ip, port, Protocol.DEFAULT_TIMEOUT, PassWordTool.decodeRedisPs(password));
    }
    
    /**
     * 从redis链接池获取一个redis的链接
     * @return redis的链接
     */
    public Jedis getConnect() {
        return pool.getResource();
    }
    
    /**
     * 向链接池返回一个使用完毕的链接
     * @param conn
     */
    protected void returnConnect(Jedis conn) {
        if (conn != null) {
            conn.close();
        }
    }
    
    /**
     * 向链接池返回一个异常的链接
     * @param conn
     */
    protected void returnExceptionConnection(Jedis conn) {
        if (conn != null) {
            conn.close();
        }
    }
    
    /**
     * 清理redis  过期方法 不建议使用
     */
    @Deprecated
    public void flushAll() {

        Jedis jedis = null;

        try {
            jedis = getConnect();
            jedis.flushAll();
        } catch (Exception e) {
            returnExceptionConnection(jedis);
        } finally {
            returnConnect(jedis);
        }
    }
    
    /**
     * 简单的序列化 
     * @param o
     * @return
     */
    protected String encode0(Object o) {
        return JSON.toJSONString(o, SerializerFeature.WriteClassName);
    }
    
    /**
     * 简单的反序列化
     * @param s
     * @param cls
     * @return
     */
    protected <T> T decode0(String s, Class<T> cls) {
        return JSON.parseObject(s, cls);
    }
    
    /**
     * 序列化數組
     * @param ls
     * @return
     */
    protected String[] encodeList(List<Object> ls) {
        String[] res = new String[ls.size()];
        for (int i = 0; i < ls.size(); ++i) {
            res[i] = encode0(ls.get(i));
        }
        return res;
    }
    
    /**
     * 反序列化数组
     * @param ls
     * @param cls
     * @return
     */
    protected <T> List<T> decodeList(List<String> ls, Class<T> cls) {
        List<T> res = new ArrayList<>();
        for (String s : ls) {
            res.add(decode0(s, cls));
        }
        return res;
    }
    
   /**
    * 序列化map
    * @param map
    * @return
    */
    protected <T> Map<String, String> encodeMap(Map<String, T> map) {
        if (map == null) {
            return null;
        }

        Map<String, String> res = new HashMap<>();
        for (Map.Entry<String, T> entry : map.entrySet()) {
            String k = entry.getKey();
            T v = entry.getValue();

            res.put(k, encode0(v));
        }

        return res;
    }
    
    /**
     * 反序列化map
     * @param map
     * @param cls
     * @return
     */
    protected <T> Map<String, T> decodeMap(Map<String, String> map, Class<T> cls) {
        if (map == null) {
            return null;
        }

        Map<String, T> res = new HashMap<>();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            String k = entry.getKey();
            String v = entry.getValue();

            res.put(k, decode0(v, cls));
        }

        return res;
    }
    
    /**
     * 根据key 查询某字段值
     * @param key
     * @return
     */
    public String get(String key) {
        Jedis jedis = null;
        try {
            jedis = getConnect();
            return jedis.get(key);
        } catch (RuntimeException e) {
            returnExceptionConnection(jedis);
            log.error("redis exception", e);
            throw e;
        } finally {
            returnConnect(jedis);
        }
    }
    
    /**
     * 设置key的值value
     * @param key
     * @param value
     */
    public void set(String key, String value) {
        Jedis jedis = null;
        try {
            jedis = getConnect();
            jedis.set(key, value);
        } catch (RuntimeException e) {
            returnExceptionConnection(jedis);
            log.error("redis exception", e);
            throw e;
        } finally {
            returnConnect(jedis);
        }
    }
    
    /**
     * 设置key，value 过期方法 不建议使用
     * @param channel
     * @param data
     */
    @Deprecated
    public void publish(byte[] channel, byte[] data) {
        Jedis jedis = null;
        try {
            jedis = getConnect();
            jedis.publish(channel, data);
        } catch (RuntimeException e) {
            returnExceptionConnection(jedis);
            log.error("redis publish exception", e);
            throw e;
        } finally {
            returnConnect(jedis);
        }
    }
    
    /**
     * key字段是否存在 
     * @param key
     * @return
     */
    public boolean exists(String key) {
        Jedis jedis = null;
        try {
            jedis = getConnect();
            return jedis.exists(key);
        } catch (RuntimeException e) {
            returnExceptionConnection(jedis);
            log.error("redis exception", e);
            throw e;
        } finally {
            returnConnect(jedis);
        }
    }
    
    /**
     * 列表右测插入数据
     * @param key
     * @param values
     */
    public void rpush(String key, String... values) {
        Jedis jedis = null;
        try {
            jedis = getConnect();
            jedis.rpushx(key, values);
        } catch (RuntimeException e) {
            returnExceptionConnection(jedis);
            log.error("redis exception", e);
            throw e;
        } finally {
            returnConnect(jedis);
        }
    }
    
    /***
     * 查询列表范围
     * @param key
     * @param start
     * @param end
     * @return
     */
    public List<String> lrange(String key, int start, int end) {
        Jedis jedis = null;
        try {
            jedis = getConnect();
            return jedis.lrange(key, start, end);
        } catch (RuntimeException e) {
            returnExceptionConnection(jedis);
            log.error("redis exception", e);
            throw e;
        } finally {
            returnConnect(jedis);
        }
    }
    
    /**
     * hSet方法
     * @param key
     * @param field
     * @param value
     */
    public void hSet(String key, String field, String value) {
        Jedis jedis = null;
        try {
            jedis = getConnect();
            jedis.hset(key, field, value);
        } catch (RuntimeException e) {
            returnExceptionConnection(jedis);
            log.error("redis exception", e);
            throw e;
        } finally {
            returnConnect(jedis);
        }
    }
    
    /**
     * hGet 方法
     * @param key
     * @param field
     * @return
     */
    public String hGet(String key, String field) {
        Jedis jedis = null;
        try {
            jedis = getConnect();
            return jedis.hget(key, field);
        } catch (RuntimeException e) {
            returnExceptionConnection(jedis);
            log.error("redis exception", e);
            throw e;
        } finally {
            returnConnect(jedis);
        }
    }
    
    /**
     * hGetAll 方法
     * @param key
     * @return
     */
    public Map<String, String> hGetAll(String key) {
        Jedis jedis = null;
        try {
            jedis = getConnect();
            return jedis.hgetAll(key);
        } catch (RuntimeException e) {
            returnExceptionConnection(jedis);
            log.error("redis exception", e);
            throw e;
        } finally {
            returnConnect(jedis);
        }
    }

    /**
     * hDel 方法
     * @param key
     * @param fields
     */
    public void hDel(String key, String... fields) {
        Jedis jedis = null;
        try {
            jedis = getConnect();
            jedis.hdel(key, fields);
        } catch (RuntimeException e) {
            returnExceptionConnection(jedis);
            log.error("redis exception", e);
            throw e;
        } finally {
            returnConnect(jedis);
        }
    }
    
    /**
     * 获取自增值
     * @param key
     * @return
     */
    public long incr(String key) {
        Jedis jedis = null;
        long id = 0l;
        try {
            jedis = getConnect();
            id = jedis.incr(key);
        } catch (RuntimeException e) {
            returnExceptionConnection(jedis);
            log.error("redis publish exception", e);
            throw e;
        } finally {
            returnConnect(jedis);
        }
        return id;
    }
}
