package com.example.demo.application.signup;

import com.example.demo.application.signup.domain.SignupResult;
import com.example.demo.application.signup.dto.UserEssentialInfoDto;
import com.example.demo.application.signup.service.SignupService;
import com.example.demo.user.service.UserReader;
import com.example.demo.user.domain.Country;
import com.example.demo.user.domain.University;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/signup")
public class SignupController {
    private final SignupService signupService;
    private final UserReader userReader;

    @GetMapping("/locale")
    public String locale(Model model) {
        model.addAttribute("countries", Country.values());
        return "/signup/locale";
    }
    @PostMapping("/locale")
    public String setLocale(@RequestParam String lang, HttpSession session) {
        if(lang == null) return "redirect:/loginPage/login";

        for(var c: Country.values())
            if(c.getLang().equals(lang)) session.setAttribute("country", c);

        return "redirect:/signup/v1";
    }

    @GetMapping("/v1")
    public String signupPage(@ModelAttribute("userDto") UserEssentialInfoDto userDto) {
        return "/signup/signupV1";
    }

    @PostMapping("/v1")
    public String signupV1(@Valid @ModelAttribute("userDto") UserEssentialInfoDto userDto, BindingResult bindingResult, RedirectAttributes redirectAttributes, HttpSession session) {
        if (!userDto.getPw().equals(userDto.getPwConfirm()))
            bindingResult.rejectValue("pwConfirm", "error.pwConfirm", "비밀번호가 일치하지 않습니다.");

        if (bindingResult.hasErrors()) {
            log.info("에러 발생: {}", bindingResult.getAllErrors());
            redirectAttributes.addFlashAttribute(
                    "alertMessage",
                    bindingResult.getAllErrors().get(0).getDefaultMessage()
            );
            return "redirect:/signup/v1";
        }
        var country = (Country) session.getAttribute("country");
        if(country == null) country = Country.ENGLISH;

        SignupResult result = signupService.signup(userDto.getNickname(), userDto.getId(), userDto.getPw(), country);
        log.info("signup result = {}", result);
        switch (result) {
            case SUCCESS -> {
                Long idx = userReader.findIdByUserId(userDto.getId());
                session.setAttribute("tempSignupId", idx);
                return "redirect:/signup/v2";
            }
            case DUPLICATE_ID -> bindingResult.rejectValue("id", "error.id", "이미 사용 중인 아이디입니다.");
            case DUPLICATE_NICKNAME -> bindingResult.rejectValue("nickname", "error.nickname", "이미 사용 중인 닉네임입니다.");
            case FORBIDDEN_NICKNAME -> bindingResult.rejectValue("nickname", "error.nickname", "사용할 수 없는 단어가 포함된 닉네임입니다.");
            default -> bindingResult.reject("signupError", "회원가입 중 오류가 발생했습니다. 잠시 후 다시 시도해주세요.");
        }
        return "redirect:/signup/v1";
    }
    @GetMapping("/v2")
    public String signupV2(
            HttpSession session, Model model) {
        Long tempId = (Long) session.getAttribute("tempSignupId");
        log.info(String.valueOf(tempId));

        if (tempId == null) {
            log.info("인식된 id 없음 : signup step2 -> step1");
            return "redirect:/signup/v1"; // 세션 없으면 1단계로 리다이렉트
        }

        model.addAttribute("userId", tempId);
        model.addAttribute("universities", University.values());
        return "/signup/signupV2";
    }
}
