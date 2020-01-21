package com.pjg.demo.lock;

/**
 * @author stranger_alone
 * @description
 * @date 2020/1/12 上午11:33
 */
public interface ZookeeperBaseLock {

    /**
    * @description 获取锁, 利用zookeeper创建临时节点，来获取锁
    * @date 2020/1/12 上午11:33
    */
    void tryLock(String path);

    /**
    * @description 释放锁，也就是删除临时节点，来释放锁
    * @date 2020/1/12 上午11:34
    */
    void unLock(String path);

    /**
    * @description 等待锁
    * @date 2020/1/12 上午11:52
    */
    void waitLock(String path);
}
