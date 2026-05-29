package com.example.demo.search;

import com.example.demo.community.post.domain.BoardType;
import com.example.demo.community.post.dto.ResPostDto;
import com.example.demo.lecture_info.lecture.dto.ResLectureDto;
import com.example.demo.lecture_info.lecture.LectureRepository;
import com.example.demo.lecture_info.lecture.dto.ReqLectureSearchQueryDto;
import com.example.demo.search.queryDsl.LectureQueryDsl;
import com.example.demo.search.queryDsl.PostQueryDsl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class SearchService{
    private final LectureQueryDsl lectureQueryDsl;
    private final LectureRepository lectureRepository;
    private final PostQueryDsl postQueryDsl;

    public PageGenericDto<ResLectureDto> search(String query, Pageable pageable) {
        var generic = lectureRepository.searchByQuery(query, pageable);
        return PageGenericDto.<ResLectureDto>builder().page(generic).build();
    }

    public PageGenericDto<ResLectureDto> multiSearch(ReqLectureSearchQueryDto dto, Pageable pageable) {
        var generic = lectureQueryDsl.multiSearch(dto, pageable);
        return PageGenericDto.<ResLectureDto>builder().page(generic).build();
    }

    public PageGenericDto<ResPostDto> search(String query, BoardType boardType, Pageable pageable) {
        var posts = postQueryDsl.search(query, boardType, pageable);
        return PageGenericDto.<ResPostDto>builder().page(posts).build();
    }
}
