package com.example.demo.lecture_info.classroom_lecture.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
public class ResLectureInfoDto {
    private final Long idx;
    @JsonProperty(value = "college_name")
    private final String collegeName;
    @JsonProperty(value = "classroom_name")
    private final String classroomName;
    @JsonProperty(value = "lecture_name")
    private final String lectureName;
    private final String schedule;
    @JsonProperty(value = "location_x")
    private final Float locationX;
    @JsonProperty(value = "location_y")
    private final Float locationY;
}
