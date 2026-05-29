package com.example.demo.application.signup.dto;

import com.example.demo.common.validation.annotation.ValidNickname;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserEssentialInfoDto {
    @NotBlank(message = "닉네임은 필수 입력 값입니다.")
    @Size(min = 2, max = 15, message = "2자 이상, 15자 이하로 입력해주세요.")
    @ValidNickname
    private String nickname;
    @Size(min = 6, max = 20, message = "아이디는 6자 이상, 20자 이하로 입력해주세요.")
    @NotBlank
    private String id;
    @Pattern(regexp = "^(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,20}$",
            message = "비밀번호는 8~20자의 영문 (대)소문자, 숫자, 특수문자(@$!%*?&)를 모두 포함해야 합니다.")
    @NotBlank
    private String pw;
    @NotBlank
    private String pwConfirm;
    @AssertTrue(message = "비밀번호와 일치하지 않습니다.")
    public boolean matchesPw() {
        return pw != null && pw.equals(pwConfirm);
    }
}
