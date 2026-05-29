package com.example.demo.common.error.dto;

import com.example.demo.common.error.code.ErrorCode;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Builder(access = AccessLevel.PRIVATE)
@ToString
public class ResErrorDto {
    private final String code;
    private final String msg;
    private final int status;
    private final String path;
    @Builder.Default
    @JsonProperty(value = "time_stamp")
    private final LocalDateTime timeStamp = LocalDateTime.now();
    private final List<FieldError> errors;
    public static ResErrorDto of(ErrorCode errorCode,String msg, String path, List<FieldError> errors){
        return ResErrorDto.builder()
                .code(errorCode.getCode())
                .msg(msg)
                .status(errorCode.getStatus().value())
                .path(path)
                .errors(errors)
                .build();
    }
    public static ResErrorDto of(ErrorCode errorCode, String msg, String path) {
        return of(errorCode, msg, path, null);
    }
    public static ResErrorDto of(ErrorCode errorCode, String path){
        return of(errorCode,path,null);
    }

    @Getter
    @RequiredArgsConstructor
    public static class FieldError{
        private final String field;
        private final String value;
        private final String reason;
    }
}
