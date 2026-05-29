package com.example.demo.common.advice;

import com.example.demo.common.error.code.ErrorCode;
import com.example.demo.common.error.dto.ResErrorDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class GlobalControllerHandler {
    @ExceptionHandler(value = HttpRequestMethodNotSupportedException.class)
    public String methodNotSupported(HttpServletRequest request) {
        ErrorCode code = ErrorCode.METHOD_NOT_ALLOWED;

        var error = ResErrorDto.of(code, request.getRequestURI());
        log.info("error : {}",error);
        return "/error/405";
    }
    @ExceptionHandler(value = ConstraintViolationException.class)
    public String constraintViolation(HttpServletRequest request) {
        ErrorCode code = ErrorCode.CONSTRAINT_VIOLATION;

        var error = ResErrorDto.of(code, request.getRequestURI());
        log.info("error : {}",error);
        return "/error/405";
    }
    @ExceptionHandler(value = MissingServletRequestParameterException.class)
    public String missingParam(HttpServletRequest request) {
        ErrorCode code = ErrorCode.MISSING_REQUIRED_PARAMETER;

        var error = ResErrorDto.of(code, request.getRequestURI());
        log.info("error : {}",error);
        return "/error/404";
    }
    @ExceptionHandler(value = HttpMessageNotReadableException.class)
    public String missingBody(HttpServletRequest request) {
        ErrorCode code = ErrorCode.MISSING_REQUEST_BODY;

        var error = ResErrorDto.of(code, request.getRequestURI());
        log.info("error : {}",error);
        return "/error/404";
    }
//    @ExceptionHandler(value = Exception.class)
//    public String internalServerError(HttpServletRequest request) {
//        ErrorCode code = ErrorCode.INTERNAL_SERVER_ERROR;
//
//        var error = ResErrorDto.of(code, request.getRequestURI());
//        log.info("error : {}",error);
//        return "/error/404";
//    }
}
