package com.example.demo.community.notice.history;

import com.example.demo.common.base.entity.BaseTimeEntity;
import com.example.demo.common.base.domain.ChangeType;
import com.example.demo.community.notice.NoticeEntity;
import jakarta.persistence.*;
import lombok.*;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "notice_histories")
public class NoticeHistoryEntity extends BaseTimeEntity {

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
    private NoticeEntity notice;

    @Builder
    public NoticeHistoryEntity(ChangeType changeType, String description, NoticeEntity notice) {
        this.changeType = changeType;
        this.description = description;
        this.notice = notice;
    }
}