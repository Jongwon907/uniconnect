package com.example.demo.community.post.dto;

import com.example.demo.community.post.domain.BoardType;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import java.time.LocalDateTime;

@ToString
@Getter
@Setter
@Builder
public class ResPostDto {
    private final Long idx;
    private final String name;
    private final String content;
    @JsonProperty(value = "board_type")
    private final BoardType boardType;
    @JsonProperty(value = "comment_cnt")
    private final int commentCnt;
    @JsonProperty(value = "view_cnt")
    private final Long viewCnt;
    @JsonProperty(value = "post_like_cnt")
    private final int postLikeCnt;
    private final String nickname;
    @JsonProperty(value = "created_at")
    private final LocalDateTime createdAt;
    @JsonProperty(value = "updated_at")
    private final LocalDateTime updatedAt;
}