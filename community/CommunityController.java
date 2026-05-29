package com.example.demo.community;

import com.example.demo.auth.principal.CustomUser;
import com.example.demo.community.like.service.LikeService;
import com.example.demo.community.post.domain.BoardType;
import com.example.demo.community.post.service.PostService;
import com.example.demo.user.service.UserReader;
import com.example.demo.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Controller
@RequestMapping("/community")
@RequiredArgsConstructor
@Slf4j
public class CommunityController {
    private final PostService postService;
    private final LikeService likeService;
    private final UserReader userReader;

    @GetMapping("/main")
    public String community(Model model) {
        Pageable pageable = PageRequest.of(0,4,Sort.by(Sort.Direction.DESC, "createdAt"));
        for(var boardType: BoardType.values())
            model.addAttribute(boardType.name(), postService.findByBoardTypeOrQuery(boardType,null,pageable));
        return "/community/main";
    }

    @GetMapping("/forum")
    public String forum(Model model){
        return "/community/forum";
    }

    @GetMapping("/posts/{post_id}")
    public String post(
            @PathVariable(name="post_id") Long postId,
            @AuthenticationPrincipal CustomUser principal,
            Model model
    ){
        var postDto = postService.view(postId, principal.getIdx());
        if(postDto == null) return "/community/main";

        model.addAttribute("loginUserId", principal.getIdx());
        model.addAttribute("post", postDto);
        return "/community/post";
    }
    @GetMapping("/post/write")
    public String writePost() { return "/community/post_write"; }

    @PostMapping("/posts/{post_id}/like")
    public String postLike(
            @AuthenticationPrincipal CustomUser customUser,
            @PathVariable(name="post_id") Long postId) {
        likeService.postLike(customUser.getIdx(), postId);
        URI uri = UriComponentsBuilder.fromPath("/community/posts/{post_id}")
                .buildAndExpand(String.valueOf(postId))
                .toUri();
        return "redirect:" + uri;
    }
    @PostMapping("/posts/{post_id}/comments/{comment_id}/like")
    public String commentLike(@AuthenticationPrincipal CustomUser principal,
                              @PathVariable(name="post_id") Long postId,
                              @PathVariable(name="comment_id") Long commentId) {
        likeService.commentLike(principal.getIdx(), commentId);
        URI uri = UriComponentsBuilder.fromPath("/community/posts/{post_id}")
                .buildAndExpand(String.valueOf(postId))
                .toUri();
        return "redirect:" + uri;
    }

    @ModelAttribute
    public void boardTypeAttribute(
            @RequestParam(value = "boardType", required = false) BoardType boardType,
            @AuthenticationPrincipal CustomUser customUser,
            Model model
    ) {
        model.addAttribute("boardTypes", BoardType.values());
        model.addAttribute("profile", userReader.getUserProfile(customUser.getIdx()));
        model.addAttribute("currentBoardType", boardType);
    }
}