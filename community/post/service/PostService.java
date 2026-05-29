package com.example.demo.community.post.service;

import com.example.demo.common.error.code.ErrorCode;
import com.example.demo.common.error.exception.BusinessException;
import com.example.demo.community.post.domain.BoardType;
import com.example.demo.community.post.PostEntity;
import com.example.demo.community.post.PostRepository;
import com.example.demo.community.post.dto.ResPostDto;
import com.example.demo.community.post.dto.ReqViewPostDto;
import com.example.demo.search.PageGenericDto;
import com.example.demo.search.queryDsl.PostQueryDsl;
import com.example.demo.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final PostQueryDsl queryDsl;

    public void save(Long userId, BoardType boardType, String name, String content) {
        var user = userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.RESOURCE_NOT_FOUND));

        postRepository.save(
                PostEntity.builder()
                .boardType(boardType)
                .name(name)
                .content(content)
                .user(user)
                .build());
    }

    @Transactional
    public ReqViewPostDto view(Long postId, Long userId) {
        var entity = postRepository.findById(postId)
                .orElseThrow(() -> new BusinessException(ErrorCode.RESOURCE_NOT_FOUND));
        entity.increaseViewCnt();

        return ReqViewPostDto.builder()
                .idx(entity.getIdx())
                .name(entity.getName())
                .content(entity.getContent())
                .userName(entity.getUser().getNickname())
                .isOwner(entity.getUser().getIdx().equals(userId))
                .viewCnt(entity.getViewCnt())
                .commentCnt(entity.getComments().size())
                .postLikeCnt(entity.getPostLikes().size())
                .build();
    }

    public void delete(Long postId, Long userId) {
        var post = postRepository.findById(postId)
                .orElseThrow(() -> new BusinessException(ErrorCode.RESOURCE_NOT_FOUND));
        if(!post.getUser().getIdx().equals(userId))
            throw new BusinessException(ErrorCode.METHOD_NOT_ALLOWED);

        postRepository.deleteById(postId);
    }

    @Transactional(readOnly = true)
    public PageGenericDto<ResPostDto> findPostsByUserId(Long userId, Pageable pageable) {
        Page<ResPostDto> page = postRepository.findPostsByUserId(userId,pageable);
        return PageGenericDto.<ResPostDto>builder().page(page).build();
    }

    @Transactional(readOnly = true)
    public PageGenericDto<ResPostDto> findPopularPosts() {
        var page = postRepository.findPopularPosts(Pageable.ofSize(2));
        return PageGenericDto.<ResPostDto>builder().page(page).build();
    }

    @Transactional(readOnly = true)
    public PageGenericDto<ResPostDto> findByBoardTypeOrQuery(BoardType boardType, String query, Pageable pageable) {
        var page = queryDsl.search(query, boardType, pageable);
        return PageGenericDto.<ResPostDto>builder().page(page).build();
    }
}