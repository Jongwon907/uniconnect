package com.example.demo.common.advice;

import com.example.demo.common.component.MessageConvertor;
import com.example.demo.common.dto.ResGenericDto;
import com.example.demo.common.error.code.ErrorCode;
import com.example.demo.common.error.dto.ResErrorDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@RestControllerAdvice
@RequiredArgsConstructor
@Slf4j
public class I18nRestControllerAdvice implements ResponseBodyAdvice<Object> {
    private final MessageConvertor messageConvertor;
    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        if(body instanceof ResGenericDto<?> dto && dto.getHeader().getMsg() != null) {
            String msg = messageConvertor.convert(dto.getHeader().getMsg());
            body = ResGenericDto.of(dto.getData(), msg);
        }
        if(body instanceof ResErrorDto dto && dto.getMsg() != null) {
            String msg = messageConvertor.convert(dto.getMsg());
            ErrorCode errorCode = ErrorCode.INTERNAL_SERVER_ERROR;
            for(var error: ErrorCode.values())
                if(dto.getCode().equals(error.getCode())) errorCode = error;
            body = ResErrorDto.of(errorCode, msg, dto.getPath(), dto.getErrors());
        }
        log.info("res : {}",body);
        return body;
    }
}
