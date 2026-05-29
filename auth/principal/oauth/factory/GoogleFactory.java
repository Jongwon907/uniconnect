package com.example.demo.auth.principal.oauth.factory;

import com.example.demo.auth.principal.oauth.adaptor.GoogleUser;
import com.example.demo.auth.principal.oauth.adaptor.OAuth2UserAdaptor;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component("GOOGLE")
public class GoogleFactory implements OAuth2Factory{
    @Override
    public OAuth2UserAdaptor create(Map<String, Object> attributes) {
        return new GoogleUser(attributes);
    }
}
