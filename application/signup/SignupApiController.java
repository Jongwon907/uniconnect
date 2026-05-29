package com.example.demo.application.signup;

import com.example.demo.application.signup.dto.UserAdditionalInfoDto;
import com.example.demo.common.dto.ResGenericDto;
import com.example.demo.common.error.code.ErrorCode;
import com.example.demo.common.error.exception.BusinessException;
import com.example.demo.user.service.UserReader;
import com.example.demo.user.service.UserService;
import com.example.demo.common.verify.service.VerifyService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/signup")
@RequiredArgsConstructor
@Slf4j
public class SignupApiController {
    private final UserService userService;
    private final VerifyService verifyService;
    private final UserReader userReader;

    @PostMapping("/additional_info")
    public ResponseEntity<?> signupV1(
            @Valid @RequestBody UserAdditionalInfoDto userAdditionalInfoDto,
            HttpSession session) {
        Long tempId = (Long) session.getAttribute("tempSignupId");
        if (tempId == null) {
            throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
        }
        if(userReader.existsEmail(userAdditionalInfoDto.getEmail())) {
            throw new BusinessException(ErrorCode.DUPLICATE_KEY);
        }
        verifyService.sendAuthCode(userAdditionalInfoDto.getEmail());

        var resDto = ResGenericDto.of(
                Map.of("email", userAdditionalInfoDto.getEmail())
                ,"signup.res.verify.code.sent");

        return ResponseEntity.ok(resDto);
    }

    @PostMapping("/complete")
    public ResponseEntity<?> signupV2(
            @Valid @RequestBody UserAdditionalInfoDto userAdditionalInfoDto,
            HttpSession session) {

        Long tempId = (Long) session.getAttribute("tempSignupId");
        if (tempId == null) {
            throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
        }

        var university = userAdditionalInfoDto.getUniversity();
        var email = userAdditionalInfoDto.getEmail();

        userService.updateEmailAndUniversity(tempId, email, university);
        var resDto = ResGenericDto.of(
                Map.of("university", userAdditionalInfoDto.getUniversity(),
                        "email", userAdditionalInfoDto.getEmail())
                ,"signup.res.user.update");

        return ResponseEntity.ok(resDto);
    }
}