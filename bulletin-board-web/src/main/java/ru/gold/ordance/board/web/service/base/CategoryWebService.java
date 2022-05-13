package ru.gold.ordance.board.web.service.base;

import ru.gold.ordance.board.web.api.category.*;

public interface CategoryWebService {
    CategoryGetRs findAll();
    CategoryGetRs findById(CategoryGetByIdRq rq);
    CategoryGetRs findByName(CategoryGetByNameRq rq);
    CategoryUpdateRs update(CategoryUpdateRq rq);
    CategoryDeleteByIdRs deleteById(CategoryDeleteByIdRq rq);
}
