package com.example.demo.user.service;

import com.example.demo.common.error.code.ErrorCode;
import com.example.demo.common.error.exception.BusinessException;
import com.example.demo.user.UserRepository;
import com.example.demo.user.domain.Country;
import com.example.demo.user.domain.University;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.UUID;

@RequiredArgsConstructor
@Service
@Transactional
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${file.upload-dir}")
    private String uploadDir;
    private static final String PROFILE_IMAGE_PATH_PREFIX = "/uploads/";

    public void updatePw(String email, String pw) {
        if(email.isBlank()) throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
        var entity = userRepository.findByEmail(email)
                        .orElseThrow(() -> new BusinessException(ErrorCode.RESOURCE_NOT_FOUND));
        entity.updatePw(passwordEncoder.encode(pw));
    }

    public void updateNicknameAndCountry(Long idx, String nickname,Country country) {
        var entity = userRepository.findById(idx)
                .orElseThrow(() -> new BusinessException(ErrorCode.RESOURCE_NOT_FOUND));
        if(userRepository.existsByNickname(nickname))
            throw new BusinessException(ErrorCode.DUPLICATE_KEY);

        entity.updateNicknameAndCountry(nickname, country);
    }

    public void updateEmailAndUniversity(Long idx, String email, University university) {
        var entity = userRepository.findById(idx)
                .orElseThrow(() -> new BusinessException(ErrorCode.RESOURCE_NOT_FOUND));
        if(userRepository.existsByEmailAndUniversity(email, university))
            throw new BusinessException(ErrorCode.DUPLICATE_KEY);

        entity.updateEmailAndUniversity(email,university);
    }

    public void updateProfileImage(MultipartFile multipartFile, Long userId) {
        var user = userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.RESOURCE_NOT_FOUND));

        if (multipartFile == null || multipartFile.isEmpty()) {
            throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
        }

        // 이미지 파일 검증
        String contentType = multipartFile.getContentType();
        if (contentType == null || !contentType.startsWith("image")) {
            throw new BusinessException(ErrorCode.INVALID_INPUT_VALUE);
        }

        String originalFilename = multipartFile.getOriginalFilename();

        String extension = Objects
                .requireNonNull(originalFilename)
                .substring(originalFilename.lastIndexOf("."));

        // 저장 파일명 생성
        String savedFileName = UUID.randomUUID() + extension;

        try {
            Path uploadPath = Paths.get(uploadDir);

            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            Path filePath = uploadPath.resolve(savedFileName);
            multipartFile.transferTo(filePath.toFile());
            user.setProfileImagePath("/uploads/" + savedFileName);
        } catch (IOException e) {
            throw new BusinessException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }
}
