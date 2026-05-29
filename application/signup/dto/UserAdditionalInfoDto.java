package com.example.demo.application.signup.dto;

import com.example.demo.user.domain.University;
import com.example.demo.common.validation.annotation.ValidEmail;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ValidEmail
@ToString
public class UserAdditionalInfoDto {
    @NotNull(message = "대학교 선택은 필수입니다.")
    private University university;
    @NotBlank(message = "이메일은 필수 입력입니다.")
    private String email;
}
