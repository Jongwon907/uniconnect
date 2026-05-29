package com.example.demo.search;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
public class PageGenericDto<T>{
    private final List<T> items;
    private final int totalPages;
    private final int number;
    private final int size;

    @Builder
    public PageGenericDto(Page<T> page) {
        items = page.getContent();
        totalPages = page.getTotalPages();
        number = page.getNumber();
        size = page.getSize();
    }
}
