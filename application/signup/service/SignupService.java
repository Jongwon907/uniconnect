package com.example.demo.application.signup.service;

import com.example.demo.application.signup.domain.SignupResult;
import com.example.demo.user.UserEntity;
import com.example.demo.user.UserRepository;
import com.example.demo.user.domain.AuthProvider;
import com.example.demo.user.domain.Country;
import com.example.demo.user.domain.Role;
import com.example.demo.common.validation.forbidden.ForbiddenWord;
import com.example.demo.common.validation.forbidden.ForbiddenWordRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class SignupService {
    private final ForbiddenWordRepository forbiddenWordRepository;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Transactional
    public SignupResult signup(String nickname, String id, String pw, Country country) {
        List<ForbiddenWord> forbiddenWords = forbiddenWordRepository.findAll();
        boolean hasForbiddenWord = forbiddenWords.stream()
                .anyMatch(fw -> nickname.contains(fw.getWord()));
        if (hasForbiddenWord) return SignupResult.FORBIDDEN_NICKNAME;
        if (checkLoginIdDuplicate(id)) return SignupResult.DUPLICATE_ID;
        if (checkNicknameDuplicate(nickname)) return SignupResult.DUPLICATE_NICKNAME;

        UserEntity entity = UserEntity.builder()
                .userId(id)
                .userPw(passwordEncoder.encode(pw))
                .nickname(nickname)
                .provider(AuthProvider.LOCAL)
                .country(country)
                .role(Role.GUEST).build();
        userRepository.save(entity);

        log.info("signup result = {}", entity);
        return SignupResult.SUCCESS;
    }

    public boolean checkLoginIdDuplicate(String Id) {
        return userRepository.existsByUserId(Id);
    }
    public boolean checkNicknameDuplicate(String nickname) {
        return userRepository.existsByNickname(nickname);
    }
}
