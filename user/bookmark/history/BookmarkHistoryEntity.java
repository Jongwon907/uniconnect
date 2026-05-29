//package com.example.Lecture.object.user.bookmark.history;
//
//
//import com.example.Lecture.base.entity.BaseTimeEntity;
//import com.example.Lecture.object.community.ChangeType;
//import com.example.Lecture.object.user.bookmark.BookmarkEntity;
//import jakarta.persistence.*;
//import lombok.*;
//
//@ToString
//@Getter
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
//@Entity
//@Table(name = "bookmark_histories")
////public class BookmarkHistoryEntity extends BaseTimeEntity {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "history_id")
//    private Long historyId;
//
//    @Enumerated(EnumType.STRING)
//    private ChangeType changeType;
//
//    @Column(length = 500)
//    private String description;
//
//    @Setter
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "idx")
//    private BookmarkEntity bookmark;
//
//    @Builder
//    public BookmarkHistoryEntity(ChangeType changeType, String description, BookmarkEntity bookmark) {
//        this.changeType = changeType;
//        this.description = description;
//        this.bookmark = bookmark;
//    }
//}
