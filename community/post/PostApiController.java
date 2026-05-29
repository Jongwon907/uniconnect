package com.example.demo.community.post;

import com.example.demo.auth.principal.CustomUser;
import com.example.demo.common.dto.ResGenericDto;
import com.example.demo.common.error.code.ErrorCode;
import com.example.demo.common.error.exception.BusinessException;
import com.example.demo.community.post.domain.BoardType;
import com.example.demo.community.post.dto.ReqWritePostDto;
import com.example.demo.community.post.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostApiController {
    private final PostService postService;

    @GetMapping
    public ResponseEntity<?> searchPost(
            @RequestParam(required = false) String query,
            @RequestParam(required = false) BoardType boardType,
            @PageableDefault(sort = "createdAt",direction = Sort.Direction.DESC) Pageable pageable) {
        return ResponseEntity.ok(postService.findByBoardTypeOrQuery(boardType,query,pageable));
    }
    @GetMapping("/popular")
    public ResponseEntity<?> getPopularPosts() {
        return ResponseEntity.ok(postService.findPopularPosts());
    }
    @GetMapping("/{user_id}")
    public ResponseEntity<?> getUserPosts(
            @PathVariable("user_id") Long userId,
            @AuthenticationPrincipal CustomUser customUser,
            @PageableDefault(size = 5,sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        if(!userId.equals(customUser.getIdx())) throw new BusinessException(ErrorCode.FORBIDDEN);
        return ResponseEntity.ok(postService.findPostsByUserId(userId, pageable));
    }

    @DeleteMapping("/{post_id}")
    public ResponseEntity<?> deletePost(
            @PathVariable(name = "post_id") Long postId,
            @AuthenticationPrincipal CustomUser customUser) {
        postService.delete(postId, customUser.getIdx());
        var resDto = ResGenericDto.of(postId, "community.res.post.delete");

        return ResponseEntity.ok(resDto);
    }

    @PostMapping
    public ResponseEntity<?> writePost(
            @RequestBody @Valid ReqWritePostDto reqWritePostDto,
            @AuthenticationPrincipal CustomUser customerUser
    ) {
        postService.save(
                customerUser.getIdx(),
                reqWritePostDto.getBoardType(),
                reqWritePostDto.getName(),
                reqWritePostDto.getContent());

        var data = ResGenericDto.of(reqWritePostDto, "community.res.post.create");
        return ResponseEntity.ok(data);
    }
}
