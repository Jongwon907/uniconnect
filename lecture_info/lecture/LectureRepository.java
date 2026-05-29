package com.example.demo.lecture_info.lecture;

import com.example.demo.lecture_info.lecture.dto.ResLectureDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface LectureRepository extends JpaRepository<LectureEntity, Long>{
    @Query("""
             select new com.example.demo.lecture_info.lecture.dto.ResLectureDto(
                l.idx,
                l.name,
                l.code,
                l.category,
                l.grade,
                l.credit,
                l.foreignSupport,
                l.professor.name,
                l.schedule
             )
             from LectureEntity l
             where l.name like concat('%',:query,'%')
             or l.professor.name like concat('%',:query,'%')
            """)
    Page<ResLectureDto> searchByQuery(@Param("query")String query,Pageable pageable);
}
