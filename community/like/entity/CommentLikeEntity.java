package com.example.demo.community.like.entity;

import com.example.demo.common.base.entity.BaseTimeEntity;
import com.example.demo.community.comment.CommentEntity;
import com.example.demo.user.UserEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "comment_like_t")
@EntityListeners(value = { AuditingEntityListener.class })
@Getter
@NoArgsConstructor(access= AccessLevel.PROTECTED)
public class CommentLikeEntity extends BaseTimeEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @JoinColumn(name="user_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private UserEntity user;
    @JoinColumn(name="comment_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private CommentEntity comment;

    @Builder
    public CommentLikeEntity(UserEntity user, CommentEntity comment) {
        this.user = user; this.comment = comment;
    }
}
