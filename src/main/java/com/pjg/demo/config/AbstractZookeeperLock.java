package com.pjg.demo.config;

import com.pjg.demo.lock.ZookeeperBaseLock;
import lombok.extern.slf4j.Slf4j;
import org.I0Itec.zkclient.ZkClient;

/**
 * @author stranger_alone
 * @description 基于zookeeper实现的分布式锁
 * @date 2020/1/12 上午11:37
 */
@Slf4j
public abstract class AbstractZookeeperLock implements ZookeeperBaseLock {

    private ZkClient zkClient;

    @Override
    public void tryLock(String path) {
        zkClient.createEphemeral(path);
    }

    @Override
    public void unLock(String path) {
        log.info("删除节点：[{}]", path);
        zkClient.delete(path);
    }

    @Override
    abstract public void waitLock(String path);

    public ZkClient getZkClient() {
        return zkClient;
    }

    public void setZkClient(ZkClient zkClient) {
        this.zkClient = zkClient;
    }
}