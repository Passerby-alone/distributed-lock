package com.pjg.demo.controller;

import com.pjg.demo.service.DistributedLockService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author stranger_alone
 * @description TODO
 * @date 2020/1/12 下午12:27
 */
@Api(tags = "分布式锁接口")
@RestController
public class DistributedLockController {

    @Autowired
    private DistributedLockService distributedLockService;


    @ApiOperation(value = "zk分布式锁")
    @RequestMapping(value = "zookeeper.distributed.lock")
    public void zkDistributedLock() {
        distributedLockService.zkDistributedLockService();
    }

    @ApiOperation(value = "redis分布式锁")
    @RequestMapping(value = "redis.distributed.lock")
    public void redisDistributedLock() {
        distributedLockService.redisDistirbutedLockService();
    }
}
