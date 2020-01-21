package com.pjg.demo.core;

import com.pjg.demo.lock.RedisBaseLock;
import com.pjg.demo.properties.RedisPoolProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * @author stranger_alone
 * @description TODO
 * @date 2020/1/21 上午11:13
 */
@Service
@Slf4j
public class RedisLock implements RedisBaseLock {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    @Autowired
    private RedisPoolProperties redisPoolProperties;

    @Override
    public boolean tryLock(String key) {

        // expiresTime: 过期时间 1000： 给一秒的误差值
        Long expiresTime = System.currentTimeMillis() + redisPoolProperties.getRedisLockTimeout() + 1000;

        if (redisTemplate.opsForValue().setIfAbsent(key, String.valueOf(expiresTime))) {
            // 成功获取到锁
            redisTemplate.expire(key, expiresTime, TimeUnit.MILLISECONDS);
            return true;
        }
        return false;
    }

    @Override
    public boolean unLock(String key) {
        redisTemplate.delete(key);
        return true;
    }

    @Override
    public void waitLock(String key) {

        try {
            CountDownLatch countDownLatch = new CountDownLatch(1);
            // 获取到是该key的过期时间
            boolean isLock = tryLock(key);
            while (!isLock) {
                Object expiresTimeObj = redisTemplate.opsForValue().get(key);
                // 算出线程需要休眠的时间差值
                Long diffTime = Long.valueOf(String.valueOf(expiresTimeObj)) - System.currentTimeMillis();
                log.info("需要等到锁的时间：[{}]毫秒", diffTime);
                if (null != expiresTimeObj || !isLock) {
                    countDownLatch.await(diffTime, TimeUnit.MILLISECONDS);
                } else {
                    countDownLatch.countDown();
                    break;
                }
                isLock = tryLock(key);
            }
        } catch (InterruptedException e) {
            log.error(e.getMessage(), e);
        }
    }
}
