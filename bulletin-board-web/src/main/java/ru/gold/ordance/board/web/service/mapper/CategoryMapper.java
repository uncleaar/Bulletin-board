package ru.gold.ordance.board.web.service.mapper;

import ru.gold.ordance.board.web.api.category.CategoryUpdateRq;
import ru.gold.ordance.board.web.api.category.WebCategory;
import ru.gold.ordance.board.core.entity.Category;

public interface CategoryMapper {
    Category fromRequest(CategoryUpdateRq rq);

    WebCategory fromEntity(Category entity);
}
