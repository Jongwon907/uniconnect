package com.example.demo.search.queryDsl;

import com.example.demo.community.post.domain.BoardType;
import com.example.demo.community.post.dto.ResPostDto;
import com.example.demo.community.post.PostEntity;
import com.example.demo.community.post.QPostEntity;
import com.example.demo.user.QUserEntity;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Repository
@Transactional(readOnly = true)
public class PostQueryDsl{

    private final JPAQueryFactory queryFactory;
    private final QPostEntity postEntity = QPostEntity.postEntity;
    private final QUserEntity userEntity = QUserEntity.userEntity;
    PathBuilder<PostEntity> entityPath = new PathBuilder<>(PostEntity.class, "postEntity");

    public Page<ResPostDto> search(String query, BoardType boardType, Pageable pageable) {
        OrderSpecifier<?>[] orderSpecifiers = pageable.getSort().stream()
                .map(order -> {
                    PathBuilder<?> path = entityPath.get(order.getProperty(), Comparable.class);
                    return new OrderSpecifier(
                            order.isAscending() ? Order.ASC : Order.DESC,
                            path
                    );
                })
                .toArray(OrderSpecifier[]::new);
        try{
            List<Tuple> tuples = queryFactory
                    .select(postEntity,userEntity.nickname)
                    .from(postEntity)
                    .innerJoin(postEntity.user,userEntity)
                    .where(
                            titleOrContentContains(query),
                            boardTypeEq(boardType)
                    )
                    .orderBy(orderSpecifiers)
                    .offset(pageable.getOffset())
                    .limit(pageable.getPageSize())
                    .fetch();

            Long total = queryFactory
                    .select(postEntity.count())
                    .from(postEntity)
                    .where(
                            titleOrContentContains(query),
                            boardTypeEq(boardType)
                    )
                    .fetchOne();

            if(total == null) total = 0L;
            var posts = tuples.stream().map(t->{
                var p = t.get(postEntity);
                var u = t.get(userEntity.nickname);

                return ResPostDto.builder()
                        .idx(p.getIdx())
                        .boardType(p.getBoardType())
                        .content(p.getContent())
                        .name(p.getName())
                        .commentCnt(p.getComments().size())
                        .viewCnt(p.getViewCnt())
                        .nickname(u)
                        .createdAt(p.getCreatedAt())
                        .build();
            }).toList();

            return new PageImpl<>(posts, pageable, total);
        }catch(Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    private Boolean isNullOrEmpty(String param) {
        return !(param == null || param.isEmpty());
    }

    private BooleanExpression boardTypeEq(BoardType boardType) {
        return (boardType != null) ? postEntity.boardType.eq(boardType) : null;
    }
    private BooleanExpression titleOrContentContains(String query) {
        return isNullOrEmpty(query) ? postEntity.name.contains(query).or(postEntity.content.contains(query)) : null;
    }
    private BooleanExpression titleContains(String title) {
        return isNullOrEmpty(title) ? postEntity.content.contains(title) : null;
    }
    private BooleanExpression contentContains(String content) {
        return isNullOrEmpty(content) ? postEntity.content.contains(content) : null;
    }
}
