package com.pjg.demo.service;

import com.pjg.demo.core.ZookeeperDistributedLock;
import lombok.extern.slf4j.Slf4j;
import org.I0Itec.zkclient.ZkClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.concurrent.CountDownLatch;

/**
 * @author pengjinguo
 * @description TODO
 * @date 2020/1/12 上午11:58
 */
@Slf4j
@Service
public class ZookeeperDistributedLockService {

    @Value("${zookeeper.lock.node.path}")
    private String lockPath;

    @Autowired
    private ZookeeperDistributedLock zookeeperDistributedLock;

    public void handleService() {
        // step1: 首先获取分布式锁
        // step2: 如果没有获取到锁则等待锁
        // step3: 获取到锁后, 处理我们的业务
        // step4: 最后释放锁

        if (!zookeeperDistributedLock.getLock(lockPath)) {
            log.info("暂时没有获取到锁，等待锁");
            zookeeperDistributedLock.waitLock(lockPath);
        }
        log.info("正在进行业务操作");
        try {
            // 让线程等待3秒 目的是测试多线程锁的获取与释放是否达到我们的要求
//            Thread.sleep(3000);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        zookeeperDistributedLock.unLock(lockPath);
    }
}
