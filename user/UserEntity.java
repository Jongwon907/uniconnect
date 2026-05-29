package com.example.demo.user;

import com.example.demo.common.base.entity.BaseTimeEntity;
import com.example.demo.community.comment.CommentEntity;
import com.example.demo.community.like.entity.CommentLikeEntity;
import com.example.demo.community.like.entity.PostLikeEntity;
import com.example.demo.community.post.PostEntity;
import com.example.demo.user.domain.AuthProvider;
import com.example.demo.user.domain.Country;
import com.example.demo.user.domain.Role;
import com.example.demo.user.domain.University;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user_t", uniqueConstraints = @UniqueConstraint(columnNames = {"userId", "provider"}))
@NoArgsConstructor
@Getter
@EntityListeners(value = AuditingEntityListener.class)
public class UserEntity extends BaseTimeEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;
    @Column(length = 50)
    private String email;
    @Column(length = 20, nullable = false)
    private String nickname;
    @Column(nullable = false)
    private String userId;
    private String userPw;
    @Column
    @Setter
    private String profileImagePath;
    @Enumerated(EnumType.STRING)
    private Country country;
    @Enumerated(EnumType.STRING)
    private University university;
    @Enumerated(EnumType.STRING)
    private AuthProvider provider;
    @Enumerated(EnumType.STRING)
    private Role role;
    @OneToMany(mappedBy = "user", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @ToString.Exclude
    private List<PostEntity> posts = new ArrayList<>();
    @OneToMany(mappedBy = "user", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @ToString.Exclude
    private List<CommentEntity> comments = new ArrayList<>();
    @OneToMany(mappedBy = "user", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    @ToString.Exclude
    private List<PostLikeEntity> postLikes = new ArrayList<>();
    @OneToMany(mappedBy = "user", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    @ToString.Exclude
    private List<CommentLikeEntity> commentLikes = new ArrayList<>();

    @Builder
    public UserEntity(String userId,String userPw,Country country, String nickname, String email, AuthProvider provider, Role role) {
        this.userId = userId;
        this.userPw = userPw;
        this.country = country;
        this.nickname = nickname;
        this.email = email;
        this.provider = provider;
        this.role = role;
    }

    public void updateEmailAndUniversity(String email, University university) {
        this.email = email;
        this.university = university;
        this.role = Role.USER;
    }
    public void updateNicknameAndCountry(String nickname, Country country) {
        this.nickname = nickname;
        this.country = country;
    }
    public void updatePw(String userPw) { this.userPw = userPw; }
}