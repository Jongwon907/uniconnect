package com.example.demo.auth.principal.oauth.adaptor;

import lombok.Getter;
import java.util.Map;
import java.util.Optional;

@Getter
public class KakaoUser implements OAuth2UserAdaptor {

    private final String nickname;
    private final String email;

    public KakaoUser(Map<String, Object> attributes) {
        Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
        Map<String, Object> profile = (Map<String, Object>) attributes.get("properties");

        nickname = Optional
                .ofNullable((String)profile.get("nickname"))
                .orElse("KAKAO_USER");

        email = (String) kakaoAccount.get("email");
    }
}
