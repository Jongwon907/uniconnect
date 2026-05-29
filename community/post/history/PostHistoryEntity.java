package com.example.demo.community.post.history;


import com.example.demo.common.base.entity.BaseTimeEntity;
import com.example.demo.common.base.domain.ChangeType;
import com.example.demo.community.post.PostEntity;
import jakarta.persistence.*;
import lombok.*;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "post_histories")
public class PostHistoryEntity extends BaseTimeEntity {

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
    @JoinColumn(name = "post_id")
    private PostEntity post;

    @Builder
    public PostHistoryEntity(ChangeType changeType, String description, PostEntity post) {
        this.changeType = changeType;
        this.description = description;
        this.post = post;
    }
}
