package com.example.demo.application.my_page;

import com.example.demo.application.my_page.dto.ReqUpdateUserDto;
import com.example.demo.auth.principal.CustomUser;
import com.example.demo.common.dto.ResGenericDto;
import com.example.demo.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.LocaleResolver;

import java.io.IOException;
import java.util.Locale;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/mypage")
@Slf4j
public class MyPageApiController {
    private final UserService userService;
    private final LocaleResolver localeResolver;

    @PostMapping("/profile/update")
    public ResponseEntity<?> updateProfile(@Valid @RequestBody ReqUpdateUserDto userFormDto,
                                           @AuthenticationPrincipal CustomUser customUser,
                                           HttpServletResponse response,
                                           HttpServletRequest request) {
        userService.updateNicknameAndCountry(customUser.getIdx(),userFormDto.getNickname(),userFormDto.getCountry());
        localeResolver.setLocale(request, response, Locale.forLanguageTag(userFormDto.getCountry().getLang()));

        var resDto = ResGenericDto.of(userFormDto,"user.res.update");

        log.info("response type : {}", resDto);
        return ResponseEntity.ok(resDto);
    }

    @PostMapping(value = "/{user_id}/profile_image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadProfileImage(
            @PathVariable("user_id") Long userId,
            @RequestPart("image") MultipartFile image) {
        userService.updateProfileImage(image, userId);
        return ResponseEntity.ok().build();
    }
}
