package com.example.demo.common.verify;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.time.Duration;

@RequiredArgsConstructor
@Component("EmailVerificationCode")
public class EmailVerificationCodeStore implements StoreHandler{
    private final RedisTemplate<String, String> redisTemplate;

    @Override
    public void save(String email) {
        redisTemplate.opsForValue().set("email:verification:code:" + email, generateCode(), Duration.ofMinutes(5));
    }

    @Override
    public void delete(String token) {
        redisTemplate.opsForValue().getAndDelete("email:verification:code:" + token);
    }

    @Override
    public String get(String token) {
        return redisTemplate.opsForValue().get(token);
    }

    private String generateCode() {
        SecureRandom secureRandom = new SecureRandom();
        return String.format("%06d",secureRandom.nextInt(1_000_000));
    }
}
