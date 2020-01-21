package com.pjg.demo.service;

import com.pjg.demo.core.RedisLock;
import com.pjg.demo.core.ZookeeperLock;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @author stranger_alone
 * @description TODO
 * @date 2020/1/12 上午11:58
 */
@Slf4j
@Service
public class DistributedLockService {

    private final static String redisLockKey = "lock";

    @Value("${zookeeper.lock.node.path}")
    private String lockPath;

    @Autowired
    private ZookeeperLock zookeeperDistributedLock;
    @Autowired
    private RedisLock redisLock;

    public void zkDistributedLockService() {
        // step1: 首先获取分布式锁
        // step2: 如果没有获取到锁则等待锁
        // step3: 获取到锁后, 处理我们的业务
        // step4: 最后释放锁
        try {
            if (!zookeeperDistributedLock.getLock(lockPath)) {
                log.info("暂时没有获取到锁，等待锁");
                zookeeperDistributedLock.waitLock(lockPath);
            }
            log.info("正在进行业务操作");
            // 让线程等待3秒 目的是测试多线程锁的获取与释放是否达到我们的要求
          Thread.sleep(3000);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            zookeeperDistributedLock.unLock(lockPath);
        }
    }

    public void redisDistirbutedLockService() {

        try {
            if (!redisLock.tryLock(redisLockKey)) {
                log.info("暂时没有获取到redis分布式锁，等待锁");
                redisLock.waitLock(redisLockKey);
            }
            log.info("获取锁成功，进行业务操作...");
            Thread.sleep(3000);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            redisLock.unLock(redisLockKey);
        }
    }

}
