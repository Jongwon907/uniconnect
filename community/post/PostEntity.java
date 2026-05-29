package com.example.demo.community.post;


import com.example.demo.common.base.entity.BaseTimeEntity;
import com.example.demo.common.base.domain.ChangeType;
import com.example.demo.community.comment.CommentEntity;
import com.example.demo.community.like.entity.PostLikeEntity;
import com.example.demo.community.post.domain.BoardType;
import com.example.demo.community.post.history.PostHistoryEntity;
import com.example.demo.user.UserEntity;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.ArrayList;
import java.util.List;

@ToString
@Getter
@Entity
@Table(name = "post_t")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(of = {"idx"}, callSuper = false)
@EntityListeners(value = {AuditingEntityListener.class})
public class PostEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;
    @Column(nullable = false, length = 50)
    private String name;
    @Column(nullable = false, length = 1000)
    private String content;
    @Enumerated(value = EnumType.STRING)
    private BoardType boardType;

    @Column
    private Long viewCnt;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @ToString.Exclude
    @OneToMany(mappedBy = "post", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    private List<CommentEntity> comments = new ArrayList<>();

    @ToString.Exclude
    @OneToMany(mappedBy = "post", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    private List<PostLikeEntity> postLikes = new ArrayList<>();

    @ToString.Exclude
    @OneToMany(mappedBy = "post", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    private List<PostHistoryEntity> histories = new ArrayList<>();
    public void addHistory(ChangeType changeType, String description) {
        histories.add(PostHistoryEntity.builder()
                .changeType(changeType)
                .description(description)
                .post(this).build());
    }

    @Builder
    public PostEntity(String name, String content, BoardType boardType, UserEntity user) {
        this.name = name;
        this.content = content;
        this.user = user;
        this.boardType = boardType;
        this.viewCnt = 0L;
    }

    public void updateName(String name) {
        this.name = name;
    }
    public void updateContent(String content) {
        this.content = content;
    }

    public void increaseViewCnt() { ++viewCnt; }
}
