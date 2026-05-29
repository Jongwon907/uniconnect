package com.example.demo.community.like.repository;

import com.example.demo.community.like.entity.PostLikeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostLikeRepository extends JpaRepository<PostLikeEntity, Long> {
    @Query("""
            select
                pl
            from PostLikeEntity pl
            where pl.user.idx = :userId and pl.post.idx = :postId
            """)
    Optional<PostLikeEntity> findPostLikeByUserIdAndPostId(@Param("userId") Long userId,@Param("postId") Long postId);
}
