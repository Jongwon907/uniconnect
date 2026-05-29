package com.example.demo.community.post.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class ReqViewPostDto {
    private Long idx;
    private String name;
    private String content;
    private String userName;
    private Boolean isOwner;
    private Long viewCnt;
    private Integer commentCnt;
    private Integer postLikeCnt;
}
