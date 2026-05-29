package com.example.demo.common.verify.component;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.time.Duration;

@Component
@RequiredArgsConstructor
public class EmailVerifyCache {
    private final RedisTemplate<String, String> redisTemplate;
    //멀티 스레드 환경에서 안전하게 사용
//    private final Map<String, String> cacheMap = new ConcurrentHashMap<>();

    public void save(String email) { redisTemplate.opsForValue().set(email, generateCode(), Duration.ofMinutes(5)); }
    public String get(String email) { return redisTemplate.opsForValue().get(email); }
    public void remove(String email) { redisTemplate.opsForValue().getAndDelete(email); }

    private String generateCode() {
        SecureRandom secureRandom = new SecureRandom();
        return String.format("%06d",secureRandom.nextInt(1_000_000));
    }
}
