package com.example.demo.community.like.service;


import com.example.demo.common.error.code.ErrorCode;
import com.example.demo.common.error.exception.BusinessException;
import com.example.demo.community.comment.CommentRepository;
import com.example.demo.community.like.entity.CommentLikeEntity;
import com.example.demo.community.like.entity.PostLikeEntity;
import com.example.demo.community.like.repository.CommentLikeRepository;
import com.example.demo.community.like.repository.PostLikeRepository;
import com.example.demo.community.post.PostRepository;
import com.example.demo.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class LikeService {
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final PostLikeRepository postLikeRepository;
    private final CommentLikeRepository commentLikeRepository;

    public void postLike(Long userId, Long postId) {
        if(postLikeRepository.findPostLikeByUserIdAndPostId(userId, postId).isPresent()) {
            return;
        }
        var user = userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.RESOURCE_NOT_FOUND));
        var post = postRepository.findById(postId)
                .orElseThrow(() -> new BusinessException(ErrorCode.RESOURCE_NOT_FOUND));

        postLikeRepository.save(
                PostLikeEntity.builder()
                        .user(user)
                        .post(post)
                        .build());
    }

    public void commentLike(Long userId, Long commentId) {
        if(commentLikeRepository.findPostLikeByUserIdAndCommentId(userId, commentId).isPresent()) {
            return;
        }
        var user = userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.RESOURCE_NOT_FOUND));
        var comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new BusinessException(ErrorCode.RESOURCE_NOT_FOUND));

        commentLikeRepository.save(
                CommentLikeEntity.builder()
                        .user(user)
                        .comment(comment)
                        .build()
        );
    }
}
