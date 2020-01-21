package com.pjg.demo.config;

import com.pjg.demo.properties.RedisPoolProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @author stranger_alone
 * @description TODO
 * @date 2020/1/21 上午10:24
 */
@Configuration
public class RedisConfig {

    @Value("${redis.max.total}")
    private Integer redisMaxTotal;
    @Value("${redis.max.idle}")
    private Integer redisMaxIdle;
    @Value("${redis.min.idle}")
    private Integer redisMinIdle;
    @Value("${redis.server.timeout}")
    private Long redisServerTimeout;
    @Value("${redis.lock.timeout}")
    private Long redisLockTimeout;

    @Autowired
    private RedisProperties redisProperties;

    @Bean
    public RedisPoolProperties redisPoolProperties() {
        RedisPoolProperties poolProperties = RedisPoolProperties.builder()
                                                .redisMaxTotal(redisMaxTotal)
                                                .redisMaxIdle(redisMaxIdle)
                                                .redisMinIdle(redisMinIdle)
                                                .redisServerTimeout(redisServerTimeout)
                                                .redisLockTimeout(redisLockTimeout).build();
        return poolProperties;
    }

    @Bean
    public JedisPoolConfig jedisPoolConfig(RedisPoolProperties redisPoolProperties) {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(redisPoolProperties.getRedisMaxTotal());
        jedisPoolConfig.setMaxIdle(redisPoolProperties.getRedisMaxIdle());
        jedisPoolConfig.setMinIdle(redisMinIdle);
        jedisPoolConfig.setBlockWhenExhausted(true);

        return jedisPoolConfig;
    }

    @Bean
    @Primary
    @ConditionalOnMissingBean(JedisConnectionFactory.class)
    public JedisConnectionFactory jedisConnectionFactory(JedisPoolConfig jedisPoolConfig) {

        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory();
        jedisConnectionFactory.setHostName(redisProperties.getHost());
        jedisConnectionFactory.setPort(redisProperties.getPort());
        if (null != redisProperties.getPassword()) {
            jedisConnectionFactory.setPassword(redisProperties.getPassword());
        }
        jedisConnectionFactory.setPoolConfig(jedisPoolConfig);
        jedisConnectionFactory.setUsePool(true);

        return jedisConnectionFactory;
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate(JedisConnectionFactory jedisConnectionFactory) {

        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();

        RedisSerializer serializer = new StringRedisSerializer();
        redisTemplate.setConnectionFactory(jedisConnectionFactory);
        redisTemplate.setKeySerializer(serializer);
        redisTemplate.setValueSerializer(serializer);
        redisTemplate.setHashKeySerializer(serializer);
        redisTemplate.setHashValueSerializer(serializer);

        return redisTemplate;
    }
}
