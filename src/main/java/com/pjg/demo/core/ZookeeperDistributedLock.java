package com.pjg.demo.core;

import com.pjg.demo.config.AbstractZookeeperLock;
import lombok.extern.slf4j.Slf4j;
import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.CountDownLatch;

/**
 * @author pengjinguo
 * @description TODO
 * @date 2020/1/12 上午11:58
 */
@Slf4j
@Service
public class ZookeeperDistributedLock extends AbstractZookeeperLock {

    @Autowired
    public ZookeeperDistributedLock(ZkClient zkClient) {
        setZkClient(zkClient);
    }

    public boolean getLock(String path) {
        try {
            tryLock(path);
            return true;
        } catch (Exception e) {
            log.info("创建临时节点：[{}]失败...", path);
            return false;
        }
    }

    @Override
    public void waitLock(String path) {

        CountDownLatch countDown = new CountDownLatch(1);

        ZkClient zkClient = getZkClient();
        IZkDataListener listener = new IZkDataListener() {
            // 节点的数据发生改变事件
            @Override
            public void handleDataChange(String s, Object o) throws Exception {
                log.info("Node " + s + " changed, new data: " + o);
            }

            // 节点被删除事件
            @Override
            public void handleDataDeleted(String s) throws Exception {
                log.info("Node " + s + " deleted.");
                // 节点被删除，重新去获取锁，如果获取到则进行countDown操作，不阻塞线程
                if (getLock(path)) {
                    log.info("创建节点成功...获取到锁，执行countDown");
                    countDown.countDown();
                }
            }
        };

        zkClient.subscribeDataChanges(path, listener);
        try {
            if (zkClient.exists(path) || !getLock(path)) {
                countDown.await();
            }
        } catch (InterruptedException e) {
            log.error(e.getMessage(), e);
        }
        zkClient.unsubscribeDataChanges(path, listener);
    }
}
