package ru.gold.ordance.board.api.rest.heir.base;

import ru.gold.ordance.board.api.rest.AbstractBaseSearchRestController;
import ru.gold.ordance.board.api.rest.AbstractDeleteRestController;
import ru.gold.ordance.board.api.rest.AbstractUpdateRestController;
import ru.gold.ordance.board.model.api.domain.category.CategoryDeleteByIdRs;
import ru.gold.ordance.board.model.api.domain.category.CategoryGetRs;
import ru.gold.ordance.board.model.api.domain.category.CategoryUpdateRq;
import ru.gold.ordance.board.model.api.domain.category.CategoryUpdateRs;

public interface CategoryRestController extends AbstractBaseSearchRestController<CategoryGetRs>, AbstractUpdateRestController<CategoryUpdateRq, CategoryUpdateRs>, AbstractDeleteRestController<CategoryDeleteByIdRs> {
    CategoryGetRs findByName(String name);
}
