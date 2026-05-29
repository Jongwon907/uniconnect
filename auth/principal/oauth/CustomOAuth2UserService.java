package com.example.demo.auth.principal.oauth;

import com.example.demo.auth.principal.OAuth2FactoryResolver;
import com.example.demo.auth.principal.oauth.adaptor.OAuth2UserAdaptor;
import com.example.demo.common.error.code.ErrorCode;
import com.example.demo.common.error.exception.BusinessException;
import com.example.demo.user.domain.AuthProvider;
import com.example.demo.user.domain.Country;
import com.example.demo.user.domain.Role;
import com.example.demo.user.UserEntity;
import com.example.demo.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;
    private final OAuth2FactoryResolver oAuth2FactoryResolver;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest)
            throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        AuthProvider provider = AuthProvider.from(registrationId);

        OAuth2UserAdaptor userAdaptor = oAuth2FactoryResolver.resolve(provider.name(), oAuth2User.getAttributes());
        if(userAdaptor == null) throw new BusinessException(ErrorCode.INVALID_INPUT_VALUE);

        String email = userAdaptor.getEmail();
        if(email.isEmpty()) throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);

        UserEntity user = findOrCreateUser(userAdaptor, provider);
        return new CustomOAuth2User(user, oAuth2User.getAttributes());
    }

    private UserEntity findOrCreateUser(OAuth2UserAdaptor userAdaptor, AuthProvider provider) {
        return userRepository.findByUserIdAndProvider(userAdaptor.getEmail(), provider)
                .orElseGet(() -> userRepository.save(
                        UserEntity.builder()
                                .userId(userAdaptor.getEmail())
                                .nickname(userAdaptor.getNickname())
                                .provider(provider)
                                .role(Role.GUEST)
                                .country(Country.ENGLISH)
                                .build()
                ));
    }
}
