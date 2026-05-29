package com.example.demo.auth.principal.oauth.factory;

import com.example.demo.auth.principal.oauth.adaptor.KakaoUser;
import com.example.demo.auth.principal.oauth.adaptor.OAuth2UserAdaptor;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component("KAKAO")
public class KakaoFactory implements OAuth2Factory{
    @Override
    public OAuth2UserAdaptor create(Map<String, Object> attributes) {
        return new KakaoUser(attributes);
    }
}
