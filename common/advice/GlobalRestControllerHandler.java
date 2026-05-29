package com.example.demo.common.advice;

import com.example.demo.common.error.code.ErrorCode;
import com.example.demo.common.error.dto.ResErrorDto;
import com.example.demo.common.error.exception.BusinessException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@RestControllerAdvice
@RequiredArgsConstructor
@Slf4j
public class GlobalRestControllerHandler {

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<?> methodArgumentNotValid(MethodArgumentNotValidException ex,
                                              HttpServletRequest request) {
        ErrorCode code = ErrorCode.INVALID_INPUT_VALUE;
        var fields = ex.getBindingResult().getFieldErrors().stream()
                .map(this::makeDtoFieldError)
                .toList();
        var error = ResErrorDto.of(code,ex.getMessage(), request.getRequestURI(), fields);
        log.info("error: {}",error);

        return ResponseEntity.status(code.getStatus()).body(error);
    }

    @ExceptionHandler(value = MethodArgumentTypeMismatchException.class)
    public ResponseEntity<?> methodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex,
                                              HttpServletRequest request) {
        ErrorCode code = ErrorCode.INVALID_TYPE_VALUE;
        var error = ResErrorDto.of(code,ex.getMessage(), request.getRequestURI());
        log.info("error: {}",error);
        return ResponseEntity.status(code.getStatus()).body(error);
    }

    @ExceptionHandler(value = BusinessException.class)
    public ResponseEntity<?> business(BusinessException ex,
                                           HttpServletRequest request) {
        var code = ex.getErrorCode();

        var error = ResErrorDto.of(code, request.getRequestURI());
        log.info("error: {}",error);
        return ResponseEntity.status(code.getStatus()).body(error);
    }
    @ExceptionHandler(value = NoResourceFoundException.class)
    public ResponseEntity<?> noResource(HttpServletRequest request) {
        String path = request.getRequestURI();
        if(path.equals("/.well-known/appspecific/com.chrome.devtools.json")) return null;
        var code = ErrorCode.RESOURCE_NOT_FOUND;
        var error = ResErrorDto.of(code, path);
        return ResponseEntity.status(code.getStatus()).body(error);
    }
    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<?> serverError(Exception ex, HttpServletRequest request){
        var code = ErrorCode.INTERNAL_SERVER_ERROR;
        var error = ResErrorDto.of(code, request.getRequestURI());
        log.info(ex.getMessage());
        log.info("error: {}",error);
        return ResponseEntity.status(code.getStatus()).body(error);
    }

    private ResErrorDto.FieldError makeDtoFieldError(FieldError fieldError){
        return new ResErrorDto.FieldError(fieldError.getField(),
                String.valueOf(fieldError.getRejectedValue()),
                fieldError.getDefaultMessage());
    }
}
