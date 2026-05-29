package com.example.demo.community.comment.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class ResCommentDto {
    private final Long idx;
    private final String content;
    private final String nickname;
    @JsonProperty("user_id")
    private final Long userId;
    @JsonProperty("is_owner")
    private final Boolean isOwner;
    @JsonProperty("like_cnt")
    private final Long likeCnt;
    @JsonProperty("created_at")
    private final LocalDateTime createdAt;
    @JsonProperty("updated_at")
    private final LocalDateTime updatedAt;
}
