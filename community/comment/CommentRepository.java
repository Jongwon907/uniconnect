package com.example.demo.community.comment;

import com.example.demo.community.comment.dto.ResCommentDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, Long> {
    @Query("""
            select new com.example.demo.community.comment.dto.ResCommentDto(
                c.idx,
                c.content,
                c.user.nickname,
                c.user.idx,
                (c.user.idx = :user_id),
                (select count(cl) from CommentLikeEntity cl where cl.comment.idx = c.idx),
                c.createdAt,
                c.updatedAt
            )
            from CommentEntity c
            where c.post.idx = :post_id
            """)
    Page<ResCommentDto> findByPostId(@Param(value="post_id")Long postId, @Param(value="user_id")Long userId, Pageable pageable);
    @Query("""
            select new com.example.demo.community.comment.dto.ResCommentDto(
                c.idx,
                c.content,
                c.user.nickname,
                c.user.idx,
                (c.user.idx = :user_id),
                (select count(cl) from CommentLikeEntity cl where cl.comment.idx = c.idx),
                c.createdAt,
                c.updatedAt
            )
            from CommentEntity c
            where c.user.idx = :user_id
            """)
    Page<ResCommentDto> findByUserId(@Param(value="user_id")Long userId, Pageable pageable);
}
