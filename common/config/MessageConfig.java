package com.example.demo.common.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

@Configuration
public class MessageConfig {

    @Bean
    public MessageSource messageSource() {
        var ms = new ReloadableResourceBundleMessageSource();
        ms.setBasenames("classpath:/messages");
        ms.setDefaultEncoding("UTF-8");
        ms.setCacheSeconds(60); // 운영은 길게/무한도 OK, 개발은 짧게
        ms.setFallbackToSystemLocale(false);
        return ms;
    }
}
