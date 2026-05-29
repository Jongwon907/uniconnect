package com.example.demo.community.comment.history;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentHistoryRepository extends JpaRepository<CommentHistoryEntity, Long> {
}
