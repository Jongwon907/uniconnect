package com.example.demo.community.post;

import com.example.demo.community.post.domain.BoardType;

import java.time.LocalDateTime;

public interface PostProjection {
    Long getIdx();
    String getName();
    String getContent();
    BoardType getBoardType();
    LocalDateTime getCreatedAt();
}
