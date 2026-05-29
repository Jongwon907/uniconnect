package com.example.demo.search.queryDsl;


import com.example.demo.lecture_info.lecture.QLectureEntity;
import com.example.demo.lecture_info.lecture.domain.SubjectClassification;
import com.example.demo.lecture_info.lecture.dto.ResLectureDto;
import com.example.demo.lecture_info.lecture.dto.ReqLectureSearchQueryDto;
import com.example.demo.lecture_info.professor.QProfessorEntity;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
@Repository
@Slf4j
public class LectureQueryDsl {

    private final JPAQueryFactory queryFactory;
    private final QLectureEntity lectureEntity = QLectureEntity.lectureEntity;
    private final QProfessorEntity professorEntity = QProfessorEntity.professorEntity;

    public Page<ResLectureDto> multiSearch(ReqLectureSearchQueryDto dto, Pageable pageable) {
        try {
            log.info("search: {}", dto);
            List<Tuple> tuples =
                    queryFactory.select(lectureEntity,
                                    professorEntity.name)
                            .from(lectureEntity)
                            .innerJoin(lectureEntity.professor, professorEntity)
                            .where(
                                    nameContains(dto.getName()),
                                    codeEq(dto.getCode()),
                                    gradeEq(dto.getGrade()),
                                    creditEq(dto.getCredit()),
                                    categoryEq(dto.getCategory()),
                                    isForeignSupport(dto.getForeignSupport()),
                                    professorNameEq(dto.getProfessorName())
                            )
                            .orderBy(lectureEntity.code.desc())
                            .offset(pageable.getOffset())
                            .limit(pageable.getPageSize())
                            .fetch();

            var lectures = tuples.stream().map(t -> {
                var entity = t.get(lectureEntity);
                ResLectureDto resDto = null;
                if(entity != null) {
                    resDto = ResLectureDto.builder()
                            .idx(entity.getIdx())
                            .name(entity.getName())
                            .category(entity.getCategory())
                            .code(entity.getCode())
                            .grade(entity.getGrade())
                            .credit(entity.getCredit())
                            .foreignSupport(entity.getForeignSupport())
                            .professorName(t.get(professorEntity.name))
                            .schedule(entity.getSchedule())
                            .build();
                }
                return resDto;
            }).toList();

            Long total =
                    queryFactory
                            .select(lectureEntity.count())
                            .from(lectureEntity)
                            .innerJoin(lectureEntity.professor, professorEntity)
                            .where(
                                    nameContains(dto.getName()),
                                    codeEq(dto.getCode()),
                                    gradeEq(dto.getGrade()),
                                    creditEq(dto.getCredit()),
                                    categoryEq(dto.getCategory()),
                                    isForeignSupport(dto.getForeignSupport()),
                                    professorNameEq(dto.getProfessorName())
                            )
                            .fetchOne();
            if (total == null) total = 0L;
            return new PageImpl<>(lectures, pageable, total);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    private BooleanExpression nameContains(String name) {
        return !(name == null || name.isEmpty()) ? lectureEntity.name.contains(name) : null;
    }

    private BooleanExpression codeEq(Integer code) {
        return code != null ? lectureEntity.code.eq(code) : null;
    }

    private BooleanExpression categoryEq(SubjectClassification category) {
        return category != null ? lectureEntity.category.eq(category) : null;
    }

    private BooleanExpression gradeEq(Integer grade) {
        return grade != null ? lectureEntity.grade.eq(grade) : null;
    }

    private BooleanExpression creditEq(Integer credit) {
        return credit != null ? lectureEntity.credit.eq(credit) : null;
    }

    private BooleanExpression isForeignSupport(Boolean foreignSupport) {
        return foreignSupport != null ? lectureEntity.foreignSupport.eq(foreignSupport) : null;
    }

    private BooleanExpression professorNameEq(String professorName) {
        return !(professorName == null || professorName.isEmpty()) ? professorEntity.name.eq(professorName) : null;
    }
}
