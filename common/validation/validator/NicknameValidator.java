package com.example.demo.common.validation.validator;

import com.example.demo.common.validation.annotation.ValidNickname;
import com.example.demo.common.validation.forbidden.ForbiddenWord;
import com.example.demo.common.validation.forbidden.ForbiddenWordRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import java.util.List;

@RequiredArgsConstructor
public class NicknameValidator implements ConstraintValidator<ValidNickname, String> {
    private final ForbiddenWordRepository forbiddenWordRepository;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        // 1. 입력값이 비어있으면 @NotBlank에서 처리하게 둠
        if (value == null) return true;
        // 2. DB에서 모든 금지 단어 조회
        List<ForbiddenWord> forbiddenWords = forbiddenWordRepository.findAll();

        // 3. 금지 단어 포함 여부 확인 (있으면 false 반환 -> 에러 발생)
        return forbiddenWords.stream()
                .noneMatch(fw -> value.contains(fw.getWord()));
    }
}