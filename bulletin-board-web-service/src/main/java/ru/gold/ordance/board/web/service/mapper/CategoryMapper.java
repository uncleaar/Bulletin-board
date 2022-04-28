package ru.gold.ordance.board.web.service.mapper;

import ru.gold.ordance.board.model.api.domain.category.CategoryUpdateRq;
import ru.gold.ordance.board.model.api.domain.category.WebCategory;
import ru.gold.ordance.board.model.entity.domain.Category;

public interface CategoryMapper {
    Category fromRequest(CategoryUpdateRq rq);

    WebCategory fromEntity(Category entity);
}
