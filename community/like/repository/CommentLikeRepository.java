package com.example.demo.community.like.repository;

import com.example.demo.community.like.entity.CommentLikeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CommentLikeRepository extends JpaRepository<CommentLikeEntity, Long> {
    @Query("""
            select
                cl
            from CommentLikeEntity cl
            where cl.user.idx = :userId and cl.comment.idx = :commentId
            """)
    Optional<CommentLikeEntity> findPostLikeByUserIdAndCommentId(@Param("userId") Long userId, @Param("commentId") Long commentId);
}
