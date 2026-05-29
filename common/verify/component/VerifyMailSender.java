package com.example.demo.common.verify.component;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class VerifyMailSender {
    //yaml에서 JavaMailSenderImpl을 빈으로 등록 후 인터페이스로 주입
    private final JavaMailSender javaMailSender;

    public void send(String to, String code) {
        SimpleMailMessage simpleMailMessages = new SimpleMailMessage();

        simpleMailMessages.setTo(to);
        simpleMailMessages.setSubject("[서비스명] 이메일 인증 코드");
        simpleMailMessages.setText(
                "이메일 인증 코드입니다.\n\n" +
                        "인증 코드: " + code + "\n\n" +
                        "5분 이내에 입력해주세요."
        );

        javaMailSender.send(simpleMailMessages);
    }
}
