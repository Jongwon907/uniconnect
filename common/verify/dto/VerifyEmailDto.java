package com.example.demo.common.verify.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class VerifyEmailDto {
    private String email;
    private String code;
}
