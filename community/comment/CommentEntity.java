package com.example.demo.community.comment;


import com.example.demo.common.base.entity.BaseTimeEntity;
import com.example.demo.common.base.domain.ChangeType;
import com.example.demo.community.comment.history.CommentHistoryEntity;
import com.example.demo.community.like.entity.CommentLikeEntity;
import com.example.demo.community.post.PostEntity;
import com.example.demo.user.UserEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@ToString
@Getter
@Entity
@Table(name = "comment_t")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(of = {"idx"}, callSuper = false)
@EntityListeners(CommentEntityListener.class)
public class CommentEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @Column(nullable = false, length = 1000)
    private String content;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private PostEntity post;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ToString.Exclude
    @OneToMany(mappedBy = "comment", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    private List<CommentLikeEntity> commentLike = new ArrayList<>();

    @ToString.Exclude
    @OneToMany(mappedBy = "comment", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    private List<CommentHistoryEntity> histories = new ArrayList<>();
    public void addHistory(ChangeType changeType, String description) {
        histories.add(CommentHistoryEntity.builder()
                .changeType(changeType)
                .description(description)
                .comment(this).build());
    }
    @Builder
    public CommentEntity(String content, PostEntity post, UserEntity user) {
        this.content = content;
        this.post = post;
        this.user = user;
    }
}
