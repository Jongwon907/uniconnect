package com.example.demo.auth.principal.oauth;

import com.example.demo.auth.principal.CustomUser;
import com.example.demo.user.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class CustomOAuth2User implements OAuth2User, CustomUser {

    private final UserEntity userEntity;
    private final Map<String, Object> attributes;

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + userEntity.getRole().name()));
    }
    @Override
    public String getName() {
        return userEntity.getUserId();
    }

    public Long getIdx() {
        return userEntity.getIdx();
    }
    public UserEntity getUser() { return userEntity; }
}
