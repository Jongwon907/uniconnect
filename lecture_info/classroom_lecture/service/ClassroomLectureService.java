package com.example.demo.lecture_info.classroom_lecture.service;

import com.example.demo.common.error.code.ErrorCode;
import com.example.demo.common.error.exception.BusinessException;
import com.example.demo.lecture_info.classroom_lecture.ClassroomLectureRepository;
import com.example.demo.lecture_info.classroom_lecture.dto.ResLectureInfoDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClassroomLectureService {
    private final ClassroomLectureRepository classroomLectureRepository;

    public ResLectureInfoDto find(Long lectureId) {
        return classroomLectureRepository.findLectureInfoByLectureId(lectureId)
                .orElseThrow(() -> new BusinessException(ErrorCode.RESOURCE_NOT_FOUND));
    }
}
