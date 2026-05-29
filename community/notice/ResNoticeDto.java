package com.example.demo.community.notice;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@ToString
@Getter
@Setter
@Builder
public class ResNoticeDto {
    private final Long idx;
    private final String name;
    private final String content;
    @JsonProperty(value = "created_at")
    private final LocalDateTime createdAt;
    @JsonProperty(value = "updated_at")
    private final LocalDateTime updatedAt;
}
