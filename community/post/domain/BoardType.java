package com.example.demo.community.post.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum BoardType {
    INFO("enum.boardType.info","auto_stories"),
    FREE("enum.boardType.free","forum"),
    PROMOTION("enum.boardType.promotion","campaign"),
    NOTICE("enum.boardType.notice","notifications_active");

    private final String boardName;
    private final String icon;
}
