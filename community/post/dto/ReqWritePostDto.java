package com.example.demo.community.post.dto;

import com.example.demo.community.post.domain.BoardType;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
@NotNull
public class ReqWritePostDto {
    @JsonProperty(value = "board_type")
    private BoardType boardType;
    @NotBlank(message = "제목을 입력해주세요.")
    private String name;
    @NotBlank(message = "내용을 입력해주세요.")
    private String content;
}
