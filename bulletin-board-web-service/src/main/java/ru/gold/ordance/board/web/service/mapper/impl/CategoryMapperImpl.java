package ru.gold.ordance.board.web.service.mapper.impl;

import ru.gold.ordance.board.model.api.domain.category.CategoryUpdateRq;
import ru.gold.ordance.board.model.api.domain.category.WebCategory;
import ru.gold.ordance.board.model.entity.domain.Category;
import ru.gold.ordance.board.web.service.mapper.CategoryMapper;

public class CategoryMapperImpl implements CategoryMapper {
    @Override
    public Category fromRequest(CategoryUpdateRq rq) {
        return Category.builder()
                .id(rq.getEntityId())
                .name(rq.getName())
                .build();
    }

    @Override
    public WebCategory fromEntity(Category entity) {
        return WebCategory.builder()
                .entityId(entity.getId())
                .name(entity.getName())
                .build();
    }
}
