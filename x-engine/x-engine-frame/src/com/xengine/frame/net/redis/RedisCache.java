package com.xengine.frame.net.redis;

import java.util.List;
import java.util.Map;
import java.util.Set;

import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;
import redis.clients.jedis.params.SetParams;


/**
 * redis缓存
 * @author 朱华煖
 */
@Slf4j
public class RedisCache extends RedisClient{
	
    /**
     * 创建redis链接 
     * @param ip redis的ip地址
     * @param port redis的端口
     * @param maxActive 链接池的大小
     * @param maxIdle 最大保持空闲状态的链接大小
     * @param maxWait 获取jedis链接最长等待时间 （ms 毫秒）
     */
	public RedisCache(String ip, int port, int maxActive, int maxIdle, int maxWait) {
		super(ip, port, maxActive, maxIdle, maxWait);
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
	public RedisCache(String ip, int port, String password, int maxActive, int maxIdle, int maxWait) {
		super(ip, port, password, maxActive, maxIdle, maxWait);
	}
	
    /**
     * 使用默认配置创建redis链接   (默认:user=root pwd='')
     * @param ip redis的ip地址
     * @param port redis的端口
     */
	public RedisCache(String ip, int port) {
		super(ip, port);
	}
	
	/*-----------------------------------------------------------
	 *********************  基础cache  ****************************
	 ------------------------------------------------------------*/
	
	
	
	/**
	 * 检查redis 连接是否正常
	 * @return
	 */
    public boolean check() {
        Jedis jedis = null;
        try {
            jedis = getConnect();
        } catch (Exception e) {
            return false;
        } finally {
            returnConnect(jedis);
        }
        return true;
    }
	
	/**
	 *  检查key是否存在
	 * @param key
	 * @param expireTime
	 * @return
	 */
    public boolean exists(String key, int expireTime) {
        Jedis jedis = null;

        try {
            jedis = getConnect();

            if (expireTime == 0) {
                return jedis.exists(key);
            } else {
                return jedis.expire(key, expireTime) == 1;
            }

        } catch (RuntimeException e) {
            returnExceptionConnection(jedis);
            log.error("redis exception", e);
            throw e;
        } finally {
            returnConnect(jedis);
        }
    }
	
	/**
	 * redis set方法
	 * @param key
	 * @param value
	 * @param expireTime
	 */
    public void set(String key, String value, int expireTime) {
        Jedis jedis = null;
        try {
            jedis = getConnect();

            if (expireTime == 0) {
                jedis.set(key, value);
            } else {
                jedis.setex(key, expireTime, value);
            }

        } catch (RuntimeException e) {
            returnExceptionConnection(jedis);
            log.error("redis exception", e);
            throw e;
        } finally {
            returnConnect(jedis);
        }
    }
	
    /**
     * redis get方法
     * @param key
     * @param cls
     * @return
     */
    public <T> T get(String key, Class<T> cls) {
        Jedis jedis = null;

        try {
            jedis = getConnect();
            String value = jedis.get(key);
            return RedisUtils.decode(value, cls);

        } catch (RuntimeException e) {
            returnExceptionConnection(jedis);
            log.error("redis exception", e);
            throw e;
        } finally {
            returnConnect(jedis);
        }
    }
    
    /**
     * Redis 添加方法  (jedis 3.0.0版本jar包)
     * @param key
     * @param value
     * @param expireTime
     * @return
     */
    public boolean add(String key, String value, int expireTime) {
        Jedis jedis = null;
        String res;
        try {
            jedis = getConnect();
            SetParams params = SetParams.setParams();
            params.nx();
            if (expireTime != 0) {
                params.ex(expireTime);
            }
            res = jedis.set(key, value, params);
            return res == null ? false : res.equals("OK");
        } catch (RuntimeException e) {
            returnExceptionConnection(jedis);
            log.error("redis exception", e);
            throw e;
        } finally {
            returnConnect(jedis);
        }
    }
    
    /**
     * 更新方法
     * @param key
     * @param value
     * @param expireTime
     * @return
     */
    public boolean update(String key, String value, int expireTime) {
        Jedis jedis = null;
        String res;
        try {
            jedis = getConnect();
            if (expireTime == 0) {
                res = jedis.set(key, value);
            } else {
                res = jedis.setex(key, expireTime, value);
            }
            return res.equals("OK");
        } catch (RuntimeException e) {
            returnExceptionConnection(jedis);
            log.error("redis exception", e);
            throw e;
        } finally {
            returnConnect(jedis);
        }
    }
    
    /**
     * 删除方法
     * @param key
     * @return
     */
    public boolean delete(String key) {
        Jedis jedis = null;
        try {
            jedis = getConnect();
            Long res = jedis.del(key);
            return res == 1;
        } catch (RuntimeException e) {
            returnExceptionConnection(jedis);
            log.error("redis exception", e);
            throw e;
        } finally {
            returnConnect(jedis);
        }
    }

    /**
     * 根据key获取自增值
     * @param key
     * @return
     */
    public Long increase(String key) {
        Jedis jedis = null;
        try {
            jedis = getConnect();
            return jedis.incr(key);
        } catch (RuntimeException e) {
            returnExceptionConnection(jedis);
            log.error("redis exception", e);
            throw e;
        } finally {
            returnConnect(jedis);
        }
    }
    
    /**
     * 根据key 获取自减值
     * @param key
     * @return
     */
    public Long decrease(String key) {
        Jedis jedis = null;
        try {
            jedis = getConnect();
            return jedis.decr(key);
        } catch (RuntimeException e) {
            returnExceptionConnection(jedis);
            log.error("redis exception", e);
            throw e;
        } finally {
            returnConnect(jedis);
        }
    }
    
    /*------------------------------------------
     * map cache
     *------------------------------------------*/
    

    /**
     * hset
     * @param key
     * @param subkey
     * @param value
     * @param expireTime
     */
    public void mapSet(String key, String subkey, String value, int expireTime) {
        Jedis jedis = null;
        try {
            jedis = getConnect();
            jedis.hset(key, subkey, value);
            if (expireTime != 0) {
                jedis.expire(key, expireTime);
            }

        } catch (RuntimeException e) {
            returnExceptionConnection(jedis);
            log.error("redis exception, key=" + key + ",subkey=" + subkey, e);
            throw e;
        } finally {
            returnConnect(jedis);
        }
    }
    
    
    /**
     * 存储map
     * @param key
     * @param map
     * @param expireTime
     */
    public <T> void mapSetAll(String key, Map<String, T> map, int expireTime) {
        Jedis jedis = null;

        Map<String, String> map1 = encodeMap(map);
        try {
            jedis = getConnect();
            jedis.del(key);
            jedis.hmset(key, map1);
            if (expireTime != 0) {
                jedis.expire(key, expireTime);
            }

        } catch (RuntimeException e) {
            returnExceptionConnection(jedis);
            log.error("redis exception key=" + key, e);
            throw e;
        } finally {
            returnConnect(jedis);
        }
    }
    
    /**
     * <p>Title: mapSetDelay</p>
     *
     * <p>Description: Delay for one key</p>
     *
     * @param key
     * @param expireTime
     */
    public void mapSetDelay(String key, int expireTime) {
        Jedis jedis = null;
        try {
            jedis = getConnect();
            if (expireTime != 0) {
                jedis.expire(key, expireTime);
            }
        } catch (RuntimeException e) {
            returnExceptionConnection(jedis);
            log.error("redis exception", e);
            throw e;
        } finally {
            returnConnect(jedis);
        }
    }
    
    /**
     * <p>Title: mapGet</p>
     *
     * <p>Description: get one key in the map</p>
     *
     * @param key
     * @param subkey
     * @param cls
     * @return
     */
    public <T> T mapGet(String key, String subkey, Class<T> cls) {
        Jedis jedis = null;

        try {
            jedis = getConnect();
            String s = jedis.hget(key, subkey);
            return RedisUtils.decode(s, cls);
        } catch (RuntimeException e) {
            returnExceptionConnection(jedis);
            log.error("redis exception", e);
            throw e;
        } finally {
            returnConnect(jedis);
        }
    }

    /**
     * <p>Title: mGet</p>
     *
     * <p>Description: get list by keys </p>
     *
     * @param cls
     * @param key
     * @return
     */
    public <T> List<T> mGet(Class<T> cls, String... keys) {
        Jedis jedis = null;

        try {
            jedis = getConnect();
            List<String> list = jedis.mget(keys);
            return decodeList(list, cls);
        } catch (RuntimeException e) {
            returnExceptionConnection(jedis);
            log.error("redis exception", e);
            throw e;
        } finally {
            returnConnect(jedis);
        }
    }

    /**
     * <p>Title: mapGetAll</p>
     *
     * <p>Description:get one key in the map </p>
     *
     * @param key
     * @param cls
     * @return
     */
    public <T> Map<String, T> mapGetAll(String key, Class<T> cls) {
        Jedis jedis = null;

        try {
            jedis = getConnect();
            Map<String, String> res = jedis.hgetAll(key);
            return decodeMap(res, cls);
        } catch (RuntimeException e) {
            returnExceptionConnection(jedis);
            log.error("redis exception", e);
            throw e;
        } finally {
            returnConnect(jedis);
        }
    }

    /**
     * <p>Title: mapDelete</p>
     *
     * <p>Description: get one key in the map</p>
     *
     * @param key
     * @param subkey
     * @return
     */
    public boolean mapDelete(String key, String subkey) {
        Jedis jedis = null;

        try {
            jedis = getConnect();
            Long res = jedis.hdel(key, subkey);
            return res == 1;
        } catch (RuntimeException e) {
            returnExceptionConnection(jedis);
            log.error("redis exception", e);
            throw e;
        } finally {
            returnConnect(jedis);
        }
    }

    /*------------------------------------------
     * list cache
     *------------------------------------------*/

    /**
     * <p>Title: listLeftPush</p>
     *
     * <p>Description: get one key in the map</p>
     *
     * @param key
     * @param value
     * @param expireTime
     */
    public void listLeftPush(String key, String value, int expireTime) {
        Jedis jedis = null;
        try {
            jedis = getConnect();
            jedis.lpush(key, value);
            if (expireTime != 0) {
                jedis.expire(key, expireTime);
            }

        } catch (RuntimeException e) {
            returnExceptionConnection(jedis);
            log.error("redis exception", e);
            throw e;
        } finally {
            returnConnect(jedis);
        }
    }

    /**
     * <p>Title: listRightPush</p>
     *
     * <p>Description:push to the tail of list </p>
     *
     * @param key
     * @param value
     * @param expireTime
     */
    public void listRightPush(String key, String value, int expireTime) {
        Jedis jedis = null;
        try {
            jedis = getConnect();
            jedis.rpush(key, value);
            if (expireTime != 0) {
                jedis.expire(key, expireTime);
            }

        } catch (RuntimeException e) {
            returnExceptionConnection(jedis);
            log.error("redis exception", e);
            throw e;
        } finally {
            returnConnect(jedis);
        }
    }

    /**
     * <p>Title: listLeftPop</p>
     *
     * <p>Description:push to the tail of list</p>
     *
     * @param key
     * @param cls
     * @return
     */
    public <T> T listLeftPop(String key, Class<T> cls) {
        Jedis jedis = null;

        try {
            jedis = getConnect();
            String s = jedis.lpop(key);
            return RedisUtils.decode(s, cls);
        } catch (RuntimeException e) {
            returnExceptionConnection(jedis);
            log.error("redis exception", e);
            throw e;
        } finally {
            returnConnect(jedis);
        }
    }

    /**
     * <p>Title: listRightPop</p>
     *
     * <p>Description: pop from the tail of list</p>
     *
     * @param key
     * @param cls
     * @return
     */
    public <T> T listRightPop(String key, Class<T> cls) {
        Jedis jedis = null;

        try {
            jedis = getConnect();
            String s = jedis.rpop(key);
            return RedisUtils.decode(s, cls);
        } catch (RuntimeException e) {
            returnExceptionConnection(jedis);
            log.error("redis exception", e);
            throw e;
        } finally {
            returnConnect(jedis);
        }
    }

    /**
     * <p>Title: listPushAll</p>
     *
     * <p>Description: set a list</p>
     *
     * @param key
     * @param ls
     * @param expireTime
     */
    public void listPushAll(String key, List<Object> ls, int expireTime) {
        Jedis jedis = null;
        String[] ls1 = encodeList(ls);
        try {
            jedis = getConnect();
            jedis.del(key);
            jedis.rpush(key, ls1);
            if (expireTime != 0) {
                jedis.expire(key, expireTime);
            }

        } catch (RuntimeException e) {
            returnExceptionConnection(jedis);
            log.error("redis exception", e);
            throw e;
        } finally {
            returnConnect(jedis);
        }
    }

    /**
     * <p>Title: listRange</p>
     *
     * <p>Description:get a range from list </p>
     *
     * @param key
     * @param begin
     * @param end
     * @param cls
     * @return
     */
    public <T> List<T> listRange(String key, int begin, int end, Class<T> cls) {
        Jedis jedis = null;
        try {
            jedis = getConnect();
            List<String> ls = jedis.lrange(key, begin, end);
            return decodeList(ls, cls);

        } catch (RuntimeException e) {
            returnExceptionConnection(jedis);
            log.error("redis exception", e);
            throw e;
        } finally {
            returnConnect(jedis);
        }
    }

    /**
     * <p>Title: keys</p>
     *
     * <p>Description: 模糊匹配所有key值</p>
     *
     * @param pattern
     * @return
     */
    public Set<String> keys(String pattern) {
        Jedis jedis = null;
        try {
            jedis = getConnect();
            return jedis.keys(pattern);
        } catch (RuntimeException e) {
            returnExceptionConnection(jedis);
            log.error("redis exception", e);
            throw e;
        } finally {
            returnConnect(jedis);
        }
    }

    /*****************************排行榜sorset*********************************************************************/
    //添加一条数据到排行榜
    public void zadd(String rankName, double score, String member) {
        Jedis jedis = null;
        try {
            jedis = getConnect();
            jedis.zadd(rankName, score, member);
        } catch (RuntimeException e) {
            returnExceptionConnection(jedis);
            log.error("redis exception", e);
            throw e;
        } finally {
            returnConnect(jedis);
        }
    }

    //从排行榜删除当前用户数据
    public void zrem(String rankName, String member) {
        Jedis jedis = null;
        try {
            jedis = getConnect();
            jedis.zrem(rankName, member);
        } catch (RuntimeException e) {
            returnExceptionConnection(jedis);
            log.error("redis exception", e);
            throw e;
        } finally {
            returnConnect(jedis);
        }
    }

    //返回排行榜成员的位置按分数值递减(从大到小)来排列。
    public Set<Tuple> zrevrange(String rankName, int start, int end) {
        Jedis jedis = null;
        try {
            jedis = getConnect();
            Set<Tuple> tuples = jedis.zrevrangeWithScores(rankName, start, end);
            return tuples;
        } catch (RuntimeException e) {
            returnExceptionConnection(jedis);
            log.error("redis exception", e);
            throw e;
        } finally {
            returnConnect(jedis);
        }
    }

    //返回排行榜指定成员的位置按分数值递减(从大到小)来排列。排名以 0 为底，也就是说， 分数值最大的成员排名为 0
    public Long zrevrank(String rankName, String member) {
        Jedis jedis = null;
        try {
            jedis = getConnect();
            Long zrevrank = jedis.zrevrank(rankName, member);
            return zrevrank;
        } catch (RuntimeException e) {
            returnExceptionConnection(jedis);
            log.error("redis exception", e);
            throw e;
        } finally {
            returnConnect(jedis);
        }
    }

    public Long setnx(String key, String value) {
        Jedis jedis = null;
        try {
            jedis = getConnect();
            return jedis.setnx(key, value);
        } catch (RuntimeException e) {
            returnExceptionConnection(jedis);
            log.error("redis exception", e);
            throw e;
        } finally {
            returnConnect(jedis);
        }
    }

    public Long expire(String key, int seconds) {
        Jedis jedis = null;
        try {
            jedis = getConnect();
            return jedis.expire(key, seconds);
        } catch (RuntimeException e) {
            returnExceptionConnection(jedis);
            log.error("redis exception", e);
            throw e;
        } finally {
            returnConnect(jedis);
        }
    }
    
    
    
    
}
