package com.example.demo.community.comment.history;

import com.example.demo.common.base.entity.BaseTimeEntity;
import com.example.demo.common.base.domain.ChangeType;
import com.example.demo.community.comment.CommentEntity;
import jakarta.persistence.*;
import lombok.*;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "comment_histories")
public class CommentHistoryEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "history_id")
    private Long historyId;

    @Enumerated(EnumType.STRING)
    private ChangeType changeType;

    @Column(length = 500)
    private String description;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idx")
    private CommentEntity comment;

    @Builder
    public CommentHistoryEntity(ChangeType changeType, String description, CommentEntity comment) {
        this.changeType = changeType;
        this.description = description;
        this.comment = comment;
    }
}