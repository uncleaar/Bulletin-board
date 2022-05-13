package ru.gold.ordance.board.web.service.mapper.impl;

import ru.gold.ordance.board.web.api.subcategory.SubcategoryUpdateRq;
import ru.gold.ordance.board.web.api.subcategory.WebSubcategory;
import ru.gold.ordance.board.core.entity.Category;
import ru.gold.ordance.board.core.entity.Subcategory;
import ru.gold.ordance.board.web.service.mapper.SubcategoryMapper;

public class SubcategoryMapperImpl implements SubcategoryMapper {
    @Override
    public Subcategory fromRequest(SubcategoryUpdateRq rq) {
        return Subcategory.builder()
                .id(rq.getEntityId())
                .name(rq.getName())
                .category(Category.builder()
                        .id(rq.getCategoryId())
                        .build())
                .build();
    }

    @Override
    public WebSubcategory fromEntity(Subcategory entity) {
        return WebSubcategory.builder()
                .entityId(entity.getId())
                .name(entity.getName())
                .categoryId(entity.getCategory().getId())
                .build();
    }
}
