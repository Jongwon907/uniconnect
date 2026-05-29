//package com.example.Lecture.object.user.bookmark;
//
//
//import com.example.Lecture.base.entity.BaseTimeEntity;
//import com.example.Lecture.object.community.posts.PostEntity;
//import com.example.Lecture.object.user.UserEntity;
//import jakarta.persistence.*;
//import lombok.*;
//
//@ToString
//@Getter
//@Entity
//@Table(name = "bookmarks")
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
//@EqualsAndHashCode(of = {"idx"}, callSuper = false)
//@EntityListeners(BookmarkEntityListener.class)
//public class BookmarkEntity extends BaseTimeEntity {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long idx;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "post_idx")
//    private PostEntity post;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "user_idx")
//    private UserEntity user;
//}
