package com.example.demo.community.post;

import com.example.demo.community.post.dto.ResPostDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<PostEntity, Long>{
    @Query("""
            select new com.example.demo.community.post.dto.ResPostDto(
                p.idx,
                p.name,
                p.content,
                p.boardType,
                size(p.comments),
                p.viewCnt,
                size(p.postLikes),
                p.user.nickname,
                p.createdAt,
                p.updatedAt
            ) from PostEntity p
            where p.user.idx = :user_idx
            """)
    Page<ResPostDto> findPostsByUserId(@Param(value = "user_idx") Long userIdx, Pageable pageable);
    @Query("""
            select new com.example.demo.community.post.dto.ResPostDto(
                p.idx,
                p.name,
                p.content,
                p.boardType,
                size(p.comments),
                p.viewCnt,
                size(p.postLikes),
                p.user.nickname,
                p.createdAt,
                p.updatedAt
            ) from PostEntity p
            order by size(p.postLikes) desc
            """)
    Page<ResPostDto> findPopularPosts(Pageable pageable);
}