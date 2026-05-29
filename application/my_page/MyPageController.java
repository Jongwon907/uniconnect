package com.example.demo.application.my_page;

import com.example.demo.auth.principal.CustomUser;
import com.example.demo.application.my_page.dto.ResMyPageUserDto;
import com.example.demo.user.service.UserReader;
import com.example.demo.user.service.UserService;
import com.example.demo.user.domain.Country;
import com.example.demo.user.domain.University;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/mypage")
@RequiredArgsConstructor
@Slf4j
public class MyPageController {
    private final UserReader userReader;

    @GetMapping
    public String myPage(@AuthenticationPrincipal CustomUser userDetails, Model model) {
        ResMyPageUserDto userDto = userReader.getMyPageUserForm(userDetails.getIdx());
        model.addAttribute("user",userDto);
        return "/mypage/mypage";
    }

    @GetMapping("/profile")
    public String profile(@AuthenticationPrincipal CustomUser userDetails, HttpSession session, Model model) {
        session.setAttribute("tempSignupId",userDetails.getIdx());

        model.addAttribute("universities", University.values());
        model.addAttribute("countries", Country.values());

        return "/mypage/profile";
    }

    @ModelAttribute
    public void boardTypeAttribute(
            @AuthenticationPrincipal CustomUser customUser,
            Model model
    ) {
        model.addAttribute("profile", userReader.getUserProfile(customUser.getIdx()));
    }
}
