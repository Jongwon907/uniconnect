package com.example.demo.auth.handler;

import com.example.demo.auth.principal.CustomUser;
import com.example.demo.user.UserRepository;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.LocaleResolver;

import java.io.IOException;
import java.util.Locale;

@Component
@RequiredArgsConstructor
public class CustomSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
    private final LocaleResolver localeResolver;
    private final UserRepository userRepository;

    @PostConstruct
    public void init() {
        setDefaultTargetUrl("/lectures");
        setAlwaysUseDefaultTargetUrl(true);
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        var lang = setLang(authentication);
        localeResolver.setLocale(request, response, Locale.forLanguageTag(lang.replace("_","-")));
        super.onAuthenticationSuccess(request, response, authentication);
    }

    private String setLang(Authentication authentication) {
        var userId = ((CustomUser) authentication.getPrincipal()).getIdx();
        if(userId == null) return "en";
        var opt = userRepository.findById(userId);
        if(opt.isEmpty() || opt.get().getCountry() == null) return "en";

        return opt.get().getCountry().getLang();
    }
}
