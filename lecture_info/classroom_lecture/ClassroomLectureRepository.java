package com.example.demo.lecture_info.classroom_lecture;

import com.example.demo.lecture_info.classroom_lecture.dto.ResLectureInfoDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ClassroomLectureRepository extends JpaRepository<ClassroomLectureEntity,Long> {
    @Query("""
            select distinct new com.example.demo.lecture_info.classroom_lecture.dto.ResLectureInfoDto(
                    co.idx,
                    co.name,
                    c.name,
                    l.name,
                    l.schedule,
                    co.locationX,
                    co.locationY
                )
            from LectureEntity l
            left join ClassroomLectureEntity cl on cl.lecture.idx = l.idx
            left join cl.classroom c
            left join c.college co
            where l.idx = :lectureId
            """)
    Optional<ResLectureInfoDto> findLectureInfoByLectureId(@Param("lectureId") Long lectureId);
}
