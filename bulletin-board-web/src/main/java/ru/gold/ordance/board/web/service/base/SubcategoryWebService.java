package ru.gold.ordance.board.web.service.base;

import ru.gold.ordance.board.web.api.subcategory.*;

public interface SubcategoryWebService {
    SubcategoryGetRs findAll();
    SubcategoryGetRs findById(SubcategoryGetByIdRq rq);
    SubcategoryGetRs findByName(SubcategoryGetByNameRq rq);
    SubcategoryUpdateRs update(SubcategoryUpdateRq rq);
    SubcategoryDeleteByIdRs deleteById(SubcategoryDeleteByIdRq rq);
}
