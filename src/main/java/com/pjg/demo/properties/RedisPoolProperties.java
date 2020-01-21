package com.pjg.demo.properties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author stranger_alone
 * @description TODO
 * @date 2020/1/21 上午10:18
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RedisPoolProperties {

    private Integer redisMaxTotal;
    private Integer redisMaxIdle;
    private Integer redisMinIdle;
    private Long redisServerTimeout;
    private Long redisLockTimeout;
}
