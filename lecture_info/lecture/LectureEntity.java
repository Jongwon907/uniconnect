package com.example.demo.lecture_info.lecture;

import com.example.demo.common.base.entity.BaseTimeEntity;
import com.example.demo.lecture_info.classroom_lecture.ClassroomLectureEntity;
import com.example.demo.lecture_info.lecture.domain.SubjectClassification;
import com.example.demo.lecture_info.professor.ProfessorEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "lecture_t")
@NoArgsConstructor
@EntityListeners(value = AuditingEntityListener.class)
@Getter
@ToString
public class LectureEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;
    @Column(nullable = false)
    private String name;
    @Column(length = 4,nullable = false)
    private Integer code;
    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private SubjectClassification category;
    @Column(nullable = false)
    private Integer grade;
    @Column(nullable = false)
    private Integer credit;
    @Column(nullable = false)
    private Boolean foreignSupport;
    private String schedule;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "professor_id", nullable = false)
    private ProfessorEntity professor;
    @OneToMany(mappedBy = "lecture", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @ToString.Exclude
    private List<ClassroomLectureEntity> classroomLectures = new ArrayList<>();
}