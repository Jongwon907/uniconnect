package com.example.demo.application.my_page.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@ToString
@RequiredArgsConstructor
public class ResMyPageUserDto {
    private final Long idx;
    private final String nickname;
    private final String email;
    @JsonProperty(value = "profile_image_path")
    private final String profileImagePath;
    @JsonProperty(value = "post_cnt")
    private final Long postCnt;
    @JsonProperty(value = "comment_cnt")
    private final Long commentCnt;
}
