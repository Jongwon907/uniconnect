package com.example.demo.common.verify;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.UUID;

@Component("PasswordResetToken")
@RequiredArgsConstructor
public class PasswordResetTokenStore implements StoreHandler{
    private final RedisTemplate<String, String> redisTemplate;

    @Override
    public void save(String token) {
        redisTemplate.opsForValue().set("password:reset:token:" + token, generateToken(), Duration.ofMinutes(5));
    }

    @Override
    public void delete(String token) {
        redisTemplate.opsForValue().getAndDelete("password:reset:token:" + token);
    }

    @Override
    public String get(String token) {
        return redisTemplate.opsForValue().get(token);
    }

    private String generateToken() {
        return UUID.randomUUID().toString();
    }
}
