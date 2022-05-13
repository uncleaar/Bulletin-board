package ru.gold.ordance.board.web.service.mapper;

import ru.gold.ordance.board.web.api.subcategory.SubcategoryUpdateRq;
import ru.gold.ordance.board.web.api.subcategory.WebSubcategory;
import ru.gold.ordance.board.core.entity.Subcategory;

public interface SubcategoryMapper {
    Subcategory fromRequest(SubcategoryUpdateRq rq);

    WebSubcategory fromEntity(Subcategory entity);
}
