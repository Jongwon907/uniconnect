package com.example.demo.user;

import com.example.demo.common.dto.ResGenericDto;
import com.example.demo.common.error.code.ErrorCode;
import com.example.demo.common.error.exception.BusinessException;
import com.example.demo.user.dto.ReqResetPwDto;
import com.example.demo.user.service.UserReader;
import com.example.demo.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserApiController {
    private final UserService userService;
    private final UserReader userReader;

    @GetMapping("/exist/user_id")
    public ResponseEntity<?> existsUserId(@RequestParam String userId) {
        if(userReader.existsUserId(userId)) throw new BusinessException(ErrorCode.DUPLICATE_KEY);
        var resDto = ResGenericDto
                .of(Map.of("userId", userId), "user.res.userId.available");
        return ResponseEntity.ok(resDto);
    }
    @GetMapping("/exist/nickname")
    public ResponseEntity<?> existsNickname(@RequestParam String nickname) {
        if(userReader.existsNickname(nickname)) throw new BusinessException(ErrorCode.DUPLICATE_KEY);
        var resDto = ResGenericDto.of(
                Map.of("nickname", nickname),
                "user.res.nickname.available");
        return ResponseEntity.ok(resDto);
    }
    @GetMapping("/user_id")
    public ResponseEntity<?> findUserIdByEmail(@RequestParam String email) {
        var userId = userReader.findUserIdByEmail(email);
        var resDto = ResGenericDto.of(userId,"user.res.userId.read");
        return ResponseEntity.ok(resDto);
    }
    @PostMapping("/user_pw")
    public ResponseEntity<?> resetUserPwByEmail(
            @Valid @RequestBody ReqResetPwDto formDto){
        userService.updatePw(formDto.getEmail(), formDto.getUserPw());
        var resDto = ResGenericDto.of(formDto, "user.res.password.update");
        return ResponseEntity.ok(resDto);
    }
}
