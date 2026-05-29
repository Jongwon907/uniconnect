package com.example.demo.lecture_info.lecture.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum SubjectClassification {
    COMMON("enum.subjectClassification.common"),
    BALANCED("enum.subjectClassification.balanced"),
    TEACHING("enum.subjectClassification.teaching"),
    FREE("enum.subjectClassification.free"),
    GENERAL_ELECTIVE("enum.subjectClassification.generalElective"),
    MAJOR_ELECTIVE("enum.subjectClassification.majorElective"),
    MAJOR_REQUIRED("enum.subjectClassification.majorRequired");

    private final String name;
}
