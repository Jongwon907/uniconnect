package com.example.demo.user.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.regex.Pattern;

@RequiredArgsConstructor
@Getter
public enum University {
    DAEGU(Pattern.compile("^[A-Za-z0-9._%+-]+@daegu\\.ac\\.kr$"),"대구대학교");

    private final Pattern emailPattern;
    private final String universityName;
    public boolean matches(String email) {
        return email != null && emailPattern.matcher(email).matches();
    }
}
