package com.example.demo.application.login;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/loginPage")
public class LoginController {

    @GetMapping("/login")
    public String login() { return "/loginPage/login";}

    @GetMapping("/find_info")
    public String findInfoPage() {
        return "/loginPage/find_id_pw";
    }

    @GetMapping("/reset_pw")
    public String resetPw() { return "/loginPage/reset_pw"; }
}
