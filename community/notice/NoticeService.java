package com.example.demo.community.notice;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Slf4j
@RequiredArgsConstructor
@Service
public class NoticeService {
    private final NoticeRepository noticeRepository;

    private final Function<ResNoticeDto, NoticeEntity> dtoToEntity =
            resNoticeDto -> NoticeEntity.builder()
                    .name(resNoticeDto.getName())
                    .content(resNoticeDto.getContent()).build();

    private final Function<NoticeEntity, ResNoticeDto> entityToDto =
            noticeEntity -> ResNoticeDto.builder()
                    .idx(noticeEntity.getIdx())
                    .name(noticeEntity.getName())
                    .content(noticeEntity.getContent())
                    .createdAt(noticeEntity.getCreatedAt())
                    .updatedAt(noticeEntity.getUpdatedAt()).build();

    public ResNoticeDto updateOrSave(ResNoticeDto dto) {
        NoticeEntity entity = Optional.ofNullable(dto.getIdx())
                .flatMap(noticeRepository::findByIdx)
                .map(e->e.updateEntity(e, dto))
                .orElseGet(()->dtoToEntity.apply(dto));
        return entityToDto.apply(noticeRepository.save(entity));
    }

    public List<ResNoticeDto> find(ResNoticeDto dto) {
        List<ResNoticeDto> search = new ArrayList<>(List.of());
        if (!noticeRepository.findByName(dto.getName()).isEmpty()) {
            noticeRepository.findByName(dto.getName())
                    .forEach(e-> search.add(entityToDto.apply(e)));
        } else {
            log.info("notice not found");
        }
        return search;
    }
}
