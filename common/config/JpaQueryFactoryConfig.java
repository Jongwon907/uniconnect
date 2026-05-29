package com.example.demo.common.config;


import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class JpaQueryFactoryConfig {

    private final EntityManager em;

    @Bean
    public JPAQueryFactory queryDslConfig() {
        return new JPAQueryFactory(em);
    }
}
