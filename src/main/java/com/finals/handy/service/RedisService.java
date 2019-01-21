package com.finals.handy.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @author zsw
 */
@Service
public class RedisService {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     * 写入redis缓存（不设置expire存活时间）
     * @param key
     * @param value
     * @return
     */
    public boolean set(final String key,String value){
        boolean result = false;
        try {
            ValueOperations<String, String> operations = redisTemplate.opsForValue();
            operations.set(key,value);
            result = true;
        }catch (Exception e){
            logger.error("写入redis缓存失败！错误信息为: " + e.getMessage());
        }
        return result;
    }

    public  boolean set(final String key,String value,Long expire){
        boolean result = false;
        try{
            System.out.println(redisTemplate);
            ValueOperations<String, String> operations = redisTemplate.opsForValue();
            operations.set(key,value);
            redisTemplate.expire(key,expire, TimeUnit.SECONDS);
            result = true;
        }catch (Exception e){
            e.printStackTrace();
            logger.error("写入redis缓存（设置expire存活时间）失败！错误信息为：" + e.getMessage());
        }
        return result;
    }

    /**
     * 读取Redis缓存
     * @param key
     * @return
     */
    public Object get(final String key){
        Object result = null;
        try{
            ValueOperations<String, String> operations = redisTemplate.opsForValue();
            result = operations.get(key);
        }catch (Exception e) {
            logger.error("读取redis缓存失败！错误信息为：" + e.getMessage());
        }
        return result;
    }

    /**
     * 判断redis缓存中是否有对应的key
     * @param key
     * @return
     */
    public boolean exist(final String key){
        boolean result = false;
        try{
            result = redisTemplate.hasKey(key);
        }catch (Exception e){
            logger.error("判断redis缓存中是否有对应的key失败！错误信息为：" + e.getMessage());
        }
        return result;
    }

    /**
     * redis根据key删除对应的value
     * @param key
     * @return
     */
    public boolean remove(final String key) {
        boolean result = false;
        try{
            if(exist(key)){
                redisTemplate.delete(key);
            }
            result = true;
        }catch (Exception e){
            logger.error("redis根据key删除对应的value失败！错误信息为：" + e.getMessage());
        }
        return result;
    }

    /**
     * Redis根据keys批量珊瑚对应的value
     * @param keys
     */
    public void remove(final String... keys) {
        for(String key : keys){
            remove(key);
        }
    }
}
