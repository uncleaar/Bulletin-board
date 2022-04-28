package ru.gold.ordance.board.web.service.base;

import ru.gold.ordance.board.model.api.domain.region.*;
import ru.gold.ordance.board.model.api.domain.subcategory.*;

public interface SubcategoryWebService {
    SubcategoryGetRs findAll();
    SubcategoryGetRs findById(SubcategoryGetByIdRq rq);
    SubcategoryGetRs findByName(SubcategoryGetByNameRq rq);
    SubcategoryUpdateRs update(SubcategoryUpdateRq rq);
    SubcategoryDeleteByIdRs deleteById(SubcategoryDeleteByIdRq rq);
}
