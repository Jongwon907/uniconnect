package com.example.demo.user.service;

import com.example.demo.application.my_page.dto.ResMyPageUserDto;
import com.example.demo.common.error.code.ErrorCode;
import com.example.demo.common.error.exception.BusinessException;
import com.example.demo.user.UserEntity;
import com.example.demo.user.UserRepository;
import com.example.demo.user.dto.ResUserProfileDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserReader {
    private final UserRepository userRepository;

    public ResMyPageUserDto getMyPageUserForm(Long userId) {
        return userRepository.findUserInfoById(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.RESOURCE_NOT_FOUND));
    }

    public ResUserProfileDto getUserProfile(Long userId) {
        return userRepository.findUserProfile(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.RESOURCE_NOT_FOUND));
    }

    public String findUserIdByEmail(String email) {
        return userRepository.findByEmail(email)
                .map(UserEntity::getUserId)
                .orElseThrow(() -> new BusinessException(ErrorCode.RESOURCE_NOT_FOUND));
    }
    public Long findIdByUserId(String userId) {
        return userRepository.findByUserId(userId)
                .map(UserEntity::getIdx)
                .orElseThrow(() -> new BusinessException(ErrorCode.RESOURCE_NOT_FOUND));
    }

    public boolean existsUserId(String userId) { return userRepository.existsByUserId(userId); }
    public boolean existsNickname(String nickname) { return userRepository.existsByNickname(nickname); }
    public boolean existsEmail(String email) { return userRepository.existsByEmail(email); }
}
