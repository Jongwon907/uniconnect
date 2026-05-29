package com.example.demo.lecture_info.classroom;


import com.example.demo.common.base.entity.BaseTimeEntity;
import com.example.demo.lecture_info.classroom_lecture.ClassroomLectureEntity;
import com.example.demo.lecture_info.college.CollegeEntity;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "classroom_t")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(value = AuditingEntityListener.class)
@Getter
public class ClassroomEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;
    private String name;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "college_id")
    private CollegeEntity college;
    @ToString.Exclude
    @OneToMany(mappedBy = "classroom", cascade = { CascadeType.PERSIST,CascadeType.MERGE})
    private List<ClassroomLectureEntity> classroomLectures = new ArrayList<>();
}
