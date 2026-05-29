package com.example.demo.application.my_page.dto;

import com.example.demo.user.domain.Country;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ReqUpdateUserDto {
    private String nickname;
    private Country country;
}
