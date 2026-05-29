package com.example.demo.user;

import com.example.demo.application.my_page.dto.ResMyPageUserDto;
import com.example.demo.user.domain.AuthProvider;
import com.example.demo.user.domain.University;
import com.example.demo.user.dto.ResUserProfileDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByUserIdAndProvider(String userId, AuthProvider provider);
    Optional<UserEntity> findByEmail(String email);
    Optional<UserEntity> findByUserId(String userId);
    boolean existsByUserId(String userId);
    boolean existsByNickname(String nickname);
    //count는 Long으로 받음
    @Query("""
            select new com.example.demo.application.my_page.dto.ResMyPageUserDto(
                u.idx,
                u.nickname,
                u.email,
                coalesce(u.profileImagePath, '/image/default-profile.png'),
                (select count(p) from PostEntity p where p.user.idx = u.idx),
                (select count(c) from CommentEntity c where c.user.idx = u.idx)
            )
            from UserEntity u
            where u.idx = :user_id
            """)
    Optional<ResMyPageUserDto> findUserInfoById(@Param(value = "user_id")Long userId);
    @Query("""
            select new com.example.demo.user.dto.ResUserProfileDto(
                coalesce(u.profileImagePath, '/image/default-profile.png'),
                u.nickname
            )
            from UserEntity u
            where u.idx = :user_id
            """)
    Optional<ResUserProfileDto> findUserProfile(@Param(value = "user_id")Long userId);
    boolean existsByEmail(String email);
    boolean existsByEmailAndUniversity(String email, University university);
}
