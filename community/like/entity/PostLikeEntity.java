package com.example.demo.community.like.entity;


import com.example.demo.common.base.entity.BaseTimeEntity;
import com.example.demo.community.post.PostEntity;
import com.example.demo.user.UserEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "post_like_t",uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "post_id"})} )
@EntityListeners(value = { AuditingEntityListener.class })
@Getter
@NoArgsConstructor(access= AccessLevel.PROTECTED)
public class PostLikeEntity extends BaseTimeEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @JoinColumn(name="user_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private UserEntity user;
    @JoinColumn(name="post_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private PostEntity post;

    @Builder
    public PostLikeEntity(UserEntity user, PostEntity post) {
        this.user = user;
        this.post = post;
    }
}
