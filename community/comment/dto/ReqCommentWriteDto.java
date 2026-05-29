package com.example.demo.community.comment.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReqCommentWriteDto {
    @NotBlank
    private String content;
}
