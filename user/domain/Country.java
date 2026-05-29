package com.example.demo.user.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Country {
    KOREA("enum.country.korea","ko")
    ,CHINA("enum.country.china","zh")
    ,CHINA_ZN("enum.country.china_cn", "zh_cn")
    ,ENGLISH("enum.country.english", "en");

    private final String countryName;
    private final String lang;
}
