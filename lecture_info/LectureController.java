package com.example.demo.lecture_info;

import com.example.demo.auth.principal.CustomUser;
import com.example.demo.community.post.domain.BoardType;
import com.example.demo.lecture_info.classroom_lecture.service.ClassroomLectureService;
import com.example.demo.user.service.UserReader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/lectures")
@RequiredArgsConstructor
@Slf4j
public class LectureController {
    private final ClassroomLectureService classroomLectureService;
    private final UserReader userReader;
    @GetMapping
    public String lecture() {
        return "/lecture/find_lecture";
    }
    @GetMapping("/{lecture_id}")
    public String classroom(@PathVariable(name = "lecture_id") Long lectureId,
                            Model model) {
        var lectureInfo = classroomLectureService.find(lectureId);
        model.addAttribute("lectureInfo", lectureInfo);
        return "/lecture/find_classroom";
    }

    @ModelAttribute
    public void boardTypeAttribute(
            @AuthenticationPrincipal CustomUser customUser,
            Model model
    ) {
        model.addAttribute("profile", userReader.getUserProfile(customUser.getIdx()));
    }
}
