package com.example.demo.common.verify.service;

import com.example.demo.common.verify.component.EmailVerifyCache;
import com.example.demo.common.verify.component.VerifyMailSender;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VerifyService {

    private final EmailVerifyCache emailVerifyCache;
    private final VerifyMailSender verifyMailSender;

    public void sendAuthCode(String email) {
        emailVerifyCache.save(email);
        verifyMailSender.send(email, emailVerifyCache.get(email));
    }
    public boolean verifyEmail(String email, String code) {
        boolean isEquals = emailVerifyCache.get(email).equals(code);
        if(isEquals) emailVerifyCache.remove(email);
        return isEquals;
    }
}
