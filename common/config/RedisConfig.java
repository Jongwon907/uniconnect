package com.example.demo.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

@Configuration
public class RedisConfig<K, V> {
    @Bean
    public RedisTemplate<K, V> redisTemplate(RedisConnectionFactory connectionFactory) {
        var template = new RedisTemplate<K, V>();
        template.setConnectionFactory(connectionFactory);
        return template;
    }
}
