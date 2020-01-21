package com.pjg.demo;

import com.pjg.demo.core.ZookeeperLock;
import org.I0Itec.zkclient.ZkClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author pengjinguo
 * @description TODO
 * @date 2020/1/12 下午12:17
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class DistributedLockTest {

    @Value("${zookeeper.address}")
    private String address;
    @Value("${zookeeper.lock.node.path}")
    private String lockPath;

    @Test
    public void testGetDistributedLock() {

        ZkClient client = new ZkClient(address);
        ZookeeperLock zookeeperDistributedLock = new ZookeeperLock(client);
        zookeeperDistributedLock.setZkClient(client);

        zookeeperDistributedLock.getLock(lockPath);
    }
}
