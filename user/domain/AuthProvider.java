package com.example.demo.user.domain;

import com.example.demo.common.error.code.ErrorCode;
import com.example.demo.common.error.exception.BusinessException;
import lombok.Getter;

@Getter
public enum AuthProvider {
    GOOGLE,
    KAKAO,
    LOCAL;

    public static AuthProvider from(String name) {
        try {
            return AuthProvider.valueOf(name.toUpperCase());
        }catch(Exception e) {
            throw new BusinessException(ErrorCode.INVALID_INPUT_VALUE);
        }
    }
}
