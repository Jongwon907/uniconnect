package com.example.demo.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReqResetPwDto {
    @NotBlank
    @Email
    private String email;
    @NotBlank
    private String userPw;
    @NotBlank
    private String pwConfirm;
}
