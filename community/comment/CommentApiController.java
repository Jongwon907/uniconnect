package com.example.demo.community.comment;

import com.example.demo.auth.principal.CustomUser;
import com.example.demo.common.dto.ResGenericDto;
import com.example.demo.common.error.code.ErrorCode;
import com.example.demo.common.error.exception.BusinessException;
import com.example.demo.community.comment.dto.ReqCommentWriteDto;
import com.example.demo.community.comment.service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CommentApiController {
    private final CommentService commentService;

    @GetMapping("/posts/{post_id}/comments")
    public ResponseEntity<?> getPostComments(
            @PathVariable(name = "post_id") Long postId,
            @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable,
            @AuthenticationPrincipal CustomUser customUser
    ) {
        return ResponseEntity.ok(commentService.findByPostId(postId,customUser.getIdx(), pageable));
    }

    @GetMapping("/comments/{user_id}")
    public ResponseEntity<?> getUserComments(
            @PathVariable(name = "user_id") Long userId,
            @AuthenticationPrincipal CustomUser customUser,
            @PageableDefault(size = 5,sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        if(!userId.equals(customUser.getIdx())) throw new BusinessException(ErrorCode.FORBIDDEN);
        return ResponseEntity.ok(commentService.findCommentsByUserId(userId, pageable));
    }
    @PostMapping("/posts/{post_id}/comments")
    public ResponseEntity<?> writeComment(
            @RequestBody @Valid ReqCommentWriteDto comment,
            @PathVariable(name = "post_id") Long postId,
            @AuthenticationPrincipal CustomUser customUser) {
        commentService.save(comment.getContent(), customUser.getIdx(), postId);
        var resDto = ResGenericDto.of(
                Map.of(
                        "postId", postId,
                        "comment", comment
                ),
                "community.res.comment.create");
        return ResponseEntity.ok(resDto);
    }
    @DeleteMapping("/comments/{comment_id}")
    public ResponseEntity<?> deleteComment(
        @PathVariable(name = "comment_id") Long commentId,
        @AuthenticationPrincipal CustomUser customUser) {
        commentService.delete(commentId, customUser.getIdx());
        var resDto = ResGenericDto.of(commentId, "community.res.comment.delete");

        return ResponseEntity.ok(resDto);
    }
}
