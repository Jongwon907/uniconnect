package com.example.demo.auth.config;

import com.example.demo.auth.handler.CustomFailureHandler;
import com.example.demo.auth.handler.CustomLogoutSuccessHandler;
import com.example.demo.auth.handler.CustomSuccessHandler;
import com.example.demo.auth.principal.oauth.CustomOAuth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@RequiredArgsConstructor
@EnableWebSecurity
@Configuration
public class SecurityConfig {
    private final CustomSuccessHandler customSuccessHandler;
    private final CustomFailureHandler customFailureHandler;
    private final CustomLogoutSuccessHandler customLogoutSuccessHandler;
    private final CustomOAuth2UserService customOAuth2UserService;
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http.csrf(CsrfConfigurer<HttpSecurity>::disable)
                .authorizeHttpRequests(auth ->
                                        auth.requestMatchers("/js/**","/signup/**",
                                                                "/loginPage/**", "/css/**",
                                                                "/images/**","/api/users/exists/**",
                                                                "/api/signup/**", "/api/verify/**").permitAll()
                                        .anyRequest().authenticated())
                .formLogin(form -> form
                                .loginPage("/loginPage/login")
                                .loginProcessingUrl("/loginPage/sessionLogin")
                                .usernameParameter("user_id")
                                .passwordParameter("password")
                                .successHandler(customSuccessHandler)
                                .failureHandler(customFailureHandler)
                                .permitAll()
                )
                .oauth2Login(oauth -> oauth
                        .userInfoEndpoint(userInfo -> userInfo.userService(customOAuth2UserService))
                        .loginPage("/loginPage/login")
                        .successHandler(customSuccessHandler)
                        .failureHandler(customFailureHandler)
                )
                .logout(logout -> logout
                        .logoutUrl("/loginPage/logout")
                        .logoutSuccessHandler(customLogoutSuccessHandler)
                        .clearAuthentication(true)
                        .invalidateHttpSession(true)
                ).build();
    }
}
