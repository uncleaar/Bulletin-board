package ru.gold.ordance.board.api.rest.heir.base;

import ru.gold.ordance.board.api.rest.AbstractBaseSearchRestController;
import ru.gold.ordance.board.api.rest.AbstractDeleteRestController;
import ru.gold.ordance.board.api.rest.AbstractUpdateRestController;
import ru.gold.ordance.board.model.api.domain.subcategory.SubcategoryDeleteByIdRs;
import ru.gold.ordance.board.model.api.domain.subcategory.SubcategoryGetRs;
import ru.gold.ordance.board.model.api.domain.subcategory.SubcategoryUpdateRq;
import ru.gold.ordance.board.model.api.domain.subcategory.SubcategoryUpdateRs;

public interface SubcategoryRestController extends AbstractBaseSearchRestController<SubcategoryGetRs>, AbstractUpdateRestController<SubcategoryUpdateRq, SubcategoryUpdateRs>, AbstractDeleteRestController<SubcategoryDeleteByIdRs> {
    SubcategoryGetRs findByName(String name);
}
