package com.example.demo.community.notice;

import com.example.demo.common.base.entity.BaseTimeEntity;
import com.example.demo.common.base.domain.ChangeType;
import com.example.demo.community.notice.history.NoticeHistoryEntity;
import com.example.demo.user.UserEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@ToString
@Getter
@Entity
@Table(name = "notice_t")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(of = {"idx"}, callSuper = false)
@EntityListeners(NoticeEntityListener.class)
public class NoticeEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(nullable = false, length = 1000)
    private String content;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;

//    @Setter
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "forum_idx")
//    private ForumEntity forumIdx;

//    @ToString.Exclude
//    @OneToMany(mappedBy = "noticeIdx", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
//    private List<CommentEntity> comments = new ArrayList<>();

    @ToString.Exclude
    @OneToMany(mappedBy = "notice", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    private List<NoticeHistoryEntity> histories = new ArrayList<>();
    public void addHistory(ChangeType changeType, String description) {
        histories.add(NoticeHistoryEntity.builder()
                .changeType(changeType)
                .description(description)
                .notice(this).build());
    }

    public void removeHistory(NoticeHistoryEntity history) {
        histories.remove(history);
        history.setNotice(null);
    }

    @Builder
    public NoticeEntity(String name, String content, UserEntity user) {
        this.name = name;
        this.content = content;
        this.user = user;
    }

    public NoticeEntity updateEntity(NoticeEntity noticeEntity, ResNoticeDto dto) {
        noticeEntity.updateNoticeName(dto.getName());
        noticeEntity.updateNoticeContents(dto.getContent());
        return noticeEntity;
    }

    public void updateNoticeName(String name) {
        this.name = name;
    }

    public void updateNoticeContents(String content) {
        this.content = content;
    }
}
