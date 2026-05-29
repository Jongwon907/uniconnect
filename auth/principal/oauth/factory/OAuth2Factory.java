package com.example.demo.auth.principal.oauth.factory;

import com.example.demo.auth.principal.oauth.adaptor.OAuth2UserAdaptor;

import java.util.Map;

public interface OAuth2Factory {
    OAuth2UserAdaptor create(Map<String, Object> attributes);
}
