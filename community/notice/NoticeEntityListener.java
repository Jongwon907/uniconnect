package com.example.demo.community.notice;

import com.example.demo.common.base.domain.ChangeType;
import com.example.demo.community.notice.history.NoticeHistoryEntity;
import com.example.demo.community.notice.history.NoticeHistoryRepository;
import com.example.demo.common.component.SpringContextHolder;
import jakarta.persistence.PostPersist;
import jakarta.persistence.PostUpdate;

public class NoticeEntityListener {

    private NoticeHistoryRepository repo() {
        return SpringContextHolder.getBean(NoticeHistoryRepository.class);
    }

    @PostPersist
    public void afterCreated(NoticeEntity noticeEntity) {
        repo().save(NoticeHistoryEntity.builder()
                .changeType(ChangeType.CREATE)
                .description("created notice [id = "+noticeEntity.getIdx()+"]")
                .notice(noticeEntity).build());
    }

    @PostUpdate
    public void afterUpdate(NoticeEntity noticeEntity) {
        repo().save(NoticeHistoryEntity.builder()
                .changeType(ChangeType.UPDATE)
                .description("updated notice [id = "+noticeEntity.getIdx()+"]")
                .notice(noticeEntity).build());
    }
}