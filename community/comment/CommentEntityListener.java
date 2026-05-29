package com.example.demo.community.comment;

import com.example.demo.common.base.domain.ChangeType;
import com.example.demo.community.comment.history.CommentHistoryEntity;
import com.example.demo.community.comment.history.CommentHistoryRepository;
import com.example.demo.common.component.SpringContextHolder;
import jakarta.persistence.PostPersist;
import jakarta.persistence.PostUpdate;

public class CommentEntityListener {

    private CommentHistoryRepository repo() {
        return SpringContextHolder.getBean(CommentHistoryRepository.class);
    }

    @PostPersist
    public void afterCreated(CommentEntity commentEntity) {
        repo().save(CommentHistoryEntity.builder()
                .changeType(ChangeType.CREATE)
                .description("created comment [id = "+commentEntity.getIdx()+"]")
                .comment(commentEntity).build());
    }

    @PostUpdate
    public void afterUpdate(CommentEntity commentEntity) {
        repo().save(CommentHistoryEntity.builder()
                .changeType(ChangeType.UPDATE)
                .description("updated comment [id = "+commentEntity.getIdx()+"]")
                .comment(commentEntity).build());
    }
}
