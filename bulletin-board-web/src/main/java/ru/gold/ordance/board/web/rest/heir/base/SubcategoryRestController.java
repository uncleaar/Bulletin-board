package ru.gold.ordance.board.web.rest.heir.base;

import ru.gold.ordance.board.web.rest.AbstractBaseSearchRestController;
import ru.gold.ordance.board.web.rest.AbstractDeleteRestController;
import ru.gold.ordance.board.web.rest.AbstractUpdateRestController;
import ru.gold.ordance.board.web.api.subcategory.SubcategoryDeleteByIdRs;
import ru.gold.ordance.board.web.api.subcategory.SubcategoryGetRs;
import ru.gold.ordance.board.web.api.subcategory.SubcategoryUpdateRq;
import ru.gold.ordance.board.web.api.subcategory.SubcategoryUpdateRs;

public interface SubcategoryRestController extends AbstractBaseSearchRestController<SubcategoryGetRs>, AbstractUpdateRestController<SubcategoryUpdateRq, SubcategoryUpdateRs>, AbstractDeleteRestController<SubcategoryDeleteByIdRs> {
    SubcategoryGetRs findByName(String name);
}
