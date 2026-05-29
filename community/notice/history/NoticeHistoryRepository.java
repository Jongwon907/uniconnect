package com.example.demo.community.notice.history;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoticeHistoryRepository extends JpaRepository<NoticeHistoryEntity, Long> {
}
