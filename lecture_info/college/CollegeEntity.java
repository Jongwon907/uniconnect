package com.example.demo.lecture_info.college;

import com.example.demo.common.base.entity.BaseTimeEntity;
import com.example.demo.lecture_info.classroom.ClassroomEntity;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "college_t")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@EntityListeners(value = AuditingEntityListener.class)
public class CollegeEntity extends BaseTimeEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;
    @Column(length = 50)
    private String name;
    @Column(name= "location_x")
    private Float locationX;
    @Column(name= "location_y")
    private Float locationY;
    @OneToMany(mappedBy = "college", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    @ToString.Exclude
    private List<ClassroomEntity> classrooms = new ArrayList<>();
}
