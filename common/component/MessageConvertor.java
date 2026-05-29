package com.example.demo.common.component;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
@RequiredArgsConstructor
public class MessageConvertor {
    private final MessageSource messageSource;
    public String convert(String code) {
        return messageSource.getMessage(code, null, LocaleContextHolder.getLocale());
    }
}
