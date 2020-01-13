package com.pjg.demo.config;

import org.I0Itec.zkclient.ZkClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author pengjinguo
 * @description TODO
 * @date 2020/1/12 下午12:39
 */
@Configuration
public class ZkClientConfig {

    @Value("${zookeeper.address}")
    private String address;

    @Bean
    public ZkClient getZkClient() {
        return new ZkClient(address);
    }


}
