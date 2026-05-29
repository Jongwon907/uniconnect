package com.example.demo.lecture_info;

import com.example.demo.lecture_info.lecture.dto.ReqLectureSearchQueryDto;
import com.example.demo.search.SearchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class LectureApiController {
    private final SearchService searchService;

    @GetMapping(value = "/lectures", params= "query")
    public ResponseEntity<?> getLecturesByQuery(
            @RequestParam String query,
            @PageableDefault(sort = "code",direction= Sort.Direction.DESC) Pageable pageable) {
        return ResponseEntity.ok(searchService.search(query, pageable));
    }

    @GetMapping("/lectures")
    public ResponseEntity<?> getLectureByMultiQuery(
            ReqLectureSearchQueryDto dto,
            @PageableDefault(sort = "code",direction= Sort.Direction.DESC) Pageable pageable) {
        return ResponseEntity.ok(searchService.multiSearch(dto, pageable));
    }
}
