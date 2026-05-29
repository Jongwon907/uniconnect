package com.example.demo.common.error.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
@ToString
public enum ErrorCode {
    INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST, "E001", "error.msg.invalid_input_value"),
    METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "E002", "error.msg.method_not_allowed"),
    INVALID_TYPE_VALUE(HttpStatus.BAD_REQUEST, "E003", "error.msg.invalid_type_value"),
    DUPLICATE_KEY(HttpStatus.CONFLICT,"E004","error.msg.duplicate_key"),
    CONSTRAINT_VIOLATION(HttpStatus.BAD_REQUEST, "E005", "error.msg.constraint_violation"),
    MISSING_REQUIRED_PARAMETER(HttpStatus.BAD_REQUEST, "E006", "error.msg.missing_required_parameter"),
    MISSING_REQUEST_BODY(HttpStatus.BAD_REQUEST, "E009", "error.msg.missing_request_body"),
    RESOURCE_NOT_FOUND(HttpStatus.NOT_FOUND, "E007", "error.msg.resource_not_found"),
    FORBIDDEN(HttpStatus.FORBIDDEN, "C002", "error.msg.forbidden"),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "C001", "error.msg.internal_server_error");

    private final HttpStatus status;
    private final String code;
    private final String message;
}
