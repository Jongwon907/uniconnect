package com.example.demo.auth.principal;

import com.example.demo.auth.principal.oauth.adaptor.OAuth2UserAdaptor;
import com.example.demo.auth.principal.oauth.factory.OAuth2Factory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class OAuth2FactoryResolver {
    private final Map<String, OAuth2Factory> factories;

    public OAuth2UserAdaptor resolve(String strategy, Map<String, Object> attributes) {
        return factories.get(strategy).create(attributes);
    }
}
