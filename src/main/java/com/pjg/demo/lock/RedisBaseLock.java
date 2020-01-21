package com.pjg.demo.lock;

/**
 * @author stranger_alone
 * @description TODO
 * @date 2020/1/21 上午10:55
 */
public interface RedisBaseLock {

    /**
     * 获取锁
     * */
    boolean tryLock(String key);

    /**
     * 释放锁
     * */
    boolean unLock(String key);

    /**
     * 等待锁释放
     * */
    void waitLock(String key);
}
