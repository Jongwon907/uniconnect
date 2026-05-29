package com.example.demo.lecture_info.lecture.dto;

import com.example.demo.lecture_info.lecture.domain.SubjectClassification;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Builder
@Getter
@Setter
@ToString
public class ResLectureDto {
    private final Long idx;
    private final String name;
    private final Integer code;
    private final SubjectClassification category;
    private final Integer grade;
    private final Integer credit;
    @JsonProperty(value = "foreign_support")
    private final Boolean foreignSupport;
    @JsonProperty(value = "professor_name")
    private final String professorName;
    private final String schedule;
}
