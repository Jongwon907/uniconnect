package com.example.demo.lecture_info.lecture.dto;

import com.example.demo.lecture_info.lecture.domain.SubjectClassification;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@ToString
@Setter
public class ReqLectureSearchQueryDto {
    private String name;
    private Integer code;
    private SubjectClassification category;
    private Integer grade;
    private Integer credit;
    private Boolean foreignSupport;
    private String professorName;
}
