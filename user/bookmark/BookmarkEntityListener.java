//package com.example.Lecture.object.user.bookmark;
//
//
//import com.example.Lecture.common.component.SpringContextHolder;
//import com.example.Lecture.object.community.ChangeType;
//import com.example.Lecture.object.user.bookmark.history.BookmarkHistoryEntity;
//import com.example.Lecture.object.user.bookmark.history.BookmarkHistoryRepository;
//import jakarta.persistence.PostPersist;
//import jakarta.persistence.PostUpdate;
//
//public class BookmarkEntityListener {
//
//    private BookmarkHistoryRepository repo() {
//        return SpringContextHolder.getBean(BookmarkHistoryRepository.class);
//    }
//
//    @PostPersist
//    public void afterCreated(BookmarkEntity bookmarkEntity) {
//
//        repo().save(BookmarkHistoryEntity.builder()
//                .changeType(ChangeType.CREATE)
//                .description("created bookmark [id = "+bookmarkEntity.getIdx()+"]")
//                .bookmark(bookmarkEntity).build());
//    }
//
//    @PostUpdate
//    public void afterUpdate(BookmarkEntity bookmarkEntity) {
//        repo().save(BookmarkHistoryEntity.builder()
//                .changeType(ChangeType.UPDATE)
//                .description("updated bookmark [id = "+bookmarkEntity.getIdx()+"]")
//                .bookmark(bookmarkEntity).build());
//    }
//}
