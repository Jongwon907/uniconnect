package com.example.demo.common.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
@ToString
public class ResGenericDto<T>{
    private final T data;
    private final Header header;

    @Builder
    @Getter
    @ToString
    public static class Header {
        private final String msg;
    }

    public static <T> ResGenericDto<T> of(T data, String msg) {
        return ResGenericDto.<T>builder()
                .data(data)
                .header(Header.builder()
                        .msg(msg)
                        .build())
                .build();
    }
}
