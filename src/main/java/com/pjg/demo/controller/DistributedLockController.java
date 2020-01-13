package com.pjg.demo.controller;

import com.pjg.demo.service.ZookeeperDistributedLockService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author pengjinguo
 * @description TODO
 * @date 2020/1/12 下午12:27
 */
@Api(tags = "分布式锁接口")
@RestController
public class DistributedLockController {

    @Autowired
    private ZookeeperDistributedLockService zookeeperDistributedLockService;


    @ApiOperation(value = "zk分布式锁")
    @RequestMapping(name = "zkDistributedLock")
    public void zkDistributedLock() {
        zookeeperDistributedLockService.handleService();
    }
}
