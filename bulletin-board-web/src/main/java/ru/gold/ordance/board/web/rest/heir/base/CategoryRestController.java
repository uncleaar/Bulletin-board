package ru.gold.ordance.board.web.rest.heir.base;

import ru.gold.ordance.board.web.rest.AbstractBaseSearchRestController;
import ru.gold.ordance.board.web.rest.AbstractDeleteRestController;
import ru.gold.ordance.board.web.rest.AbstractUpdateRestController;
import ru.gold.ordance.board.web.api.category.CategoryDeleteByIdRs;
import ru.gold.ordance.board.web.api.category.CategoryGetRs;
import ru.gold.ordance.board.web.api.category.CategoryUpdateRq;
import ru.gold.ordance.board.web.api.category.CategoryUpdateRs;

public interface CategoryRestController extends AbstractBaseSearchRestController<CategoryGetRs>, AbstractUpdateRestController<CategoryUpdateRq, CategoryUpdateRs>, AbstractDeleteRestController<CategoryDeleteByIdRs> {
    CategoryGetRs findByName(String name);
}
