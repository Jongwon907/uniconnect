package com.example.demo.community.comment.service;


import com.example.demo.common.error.code.ErrorCode;
import com.example.demo.common.error.exception.BusinessException;
import com.example.demo.community.comment.CommentEntity;
import com.example.demo.community.comment.CommentRepository;
import com.example.demo.community.comment.dto.ResCommentDto;
import com.example.demo.community.post.PostRepository;
import com.example.demo.search.PageGenericDto;
import com.example.demo.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public void save(String content, Long idx, Long postId) {
        var post = postRepository.findById(postId)
                .orElseThrow(() -> new BusinessException(ErrorCode.RESOURCE_NOT_FOUND));
        var user = userRepository.findById(idx)
                .orElseThrow(() -> new BusinessException(ErrorCode.RESOURCE_NOT_FOUND));

        commentRepository.save(
                CommentEntity.builder()
                .content(content)
                .user(user)
                .post(post).build());
    }

    public PageGenericDto<ResCommentDto> findCommentsByUserId(Long userId, Pageable pageable) {
        var page = commentRepository.findByUserId(userId, pageable);
        return PageGenericDto.<ResCommentDto>builder()
                .page(page)
                .build();
    }

    public PageGenericDto<ResCommentDto> findByPostId(Long postId, Long userId , Pageable pageable) {
        var page = commentRepository.findByPostId(postId,userId, pageable);
        return PageGenericDto.<ResCommentDto>builder()
                .page(page)
                .build();
    }

    public void delete(Long commentId, Long userId) {
        var comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new BusinessException(ErrorCode.RESOURCE_NOT_FOUND));
        if(!comment.getUser().getIdx().equals(userId))
            throw new BusinessException(ErrorCode.METHOD_NOT_ALLOWED);

        commentRepository.deleteById(commentId);
    }
}

