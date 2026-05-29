package com.example.demo.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@RequiredArgsConstructor
public class ResUserProfileDto {
    @JsonProperty(value = "profile_image_path")
    private final String profileImagePath;
    private final String nickname;

}
