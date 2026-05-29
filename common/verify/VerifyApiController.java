package com.example.demo.common.verify;

import com.example.demo.common.dto.ResGenericDto;
import com.example.demo.common.error.code.ErrorCode;
import com.example.demo.common.error.exception.BusinessException;
import com.example.demo.user.service.UserReader;
import com.example.demo.common.verify.dto.VerifyEmailDto;
import com.example.demo.common.verify.service.VerifyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/verify")
@Slf4j
public class VerifyApiController {

    private final VerifyService verifyService;
    private final UserReader userReader;

    @PostMapping("/email")
    public ResponseEntity<?> sendAuthCode(
            @RequestParam String email) {
        if(!userReader.existsEmail(email)) {
            throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
        }
        verifyService.sendAuthCode(email);
        var resDto = ResGenericDto.of(Map.of("email", email), "alert.verify.codeSend");
        return ResponseEntity.ok(resDto);
    }
    @PostMapping
    public ResponseEntity<?> checkEmail(@Valid @RequestBody VerifyEmailDto verifyEmailDto){
        if(!verifyService.verifyEmail(verifyEmailDto.getEmail(), verifyEmailDto.getCode()))
            throw new BusinessException(ErrorCode.INVALID_INPUT_VALUE);

        var resDto = ResGenericDto.of(
                Map.of(
                        "email", verifyEmailDto.getEmail(),
                        "code", verifyEmailDto.getCode())
        ,"alert.verify.success");
        return ResponseEntity.ok(resDto);
    }
}
