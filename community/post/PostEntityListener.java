package com.example.demo.community.post;

import com.example.demo.common.base.domain.ChangeType;
import com.example.demo.community.post.history.PostHistoryEntity;
import com.example.demo.community.post.history.PostHistoryRepository;
import com.example.demo.common.component.SpringContextHolder;
import jakarta.persistence.PostPersist;
import jakarta.persistence.PostUpdate;

public class PostEntityListener {

    private PostHistoryRepository repo() {
        return SpringContextHolder.getBean(PostHistoryRepository.class);
    }

    @PostPersist
    public void afterCreated(PostEntity postEntity) {
        repo().save(PostHistoryEntity.builder()
                .changeType(ChangeType.CREATE)
                .description("created post [id = "+postEntity.getIdx()+"]")
                .post(postEntity).build());
    }

    @PostUpdate
    public void afterUpdate(PostEntity postEntity) {
        repo().save(PostHistoryEntity.builder()
                .changeType(ChangeType.UPDATE)
                .description("updated post [id = "+postEntity.getIdx()+"]")
                .post(postEntity).build());
    }
}