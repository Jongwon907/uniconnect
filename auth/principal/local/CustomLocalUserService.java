package com.example.demo.auth.principal.local;

import com.example.demo.user.UserEntity;
import com.example.demo.user.UserRepository;
import com.example.demo.user.domain.AuthProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomLocalUserService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userId) {
        UserEntity userEntity = userRepository.findByUserIdAndProvider(userId, AuthProvider.LOCAL)
                .orElseThrow(() -> new UsernameNotFoundException("아이디 또는 비밀번호가 일치하지 않습니다."));

        return new CustomLocalUser(userEntity);
    }
}