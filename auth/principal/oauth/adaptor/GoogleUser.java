package com.example.demo.auth.principal.oauth.adaptor;

import lombok.Getter;

import java.util.Map;
import java.util.Optional;

@Getter
public class GoogleUser implements OAuth2UserAdaptor {

    private final String nickname;
    private final String email;

    public GoogleUser(Map<String, Object> attributes) {
        nickname = Optional.ofNullable((String) attributes.get("name"))
                .orElse("GOOGLE_USER");
        email = (String) attributes.get("email");
    }
}