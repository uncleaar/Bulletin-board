package ru.gold.ordance.board.web.service.mapper;

import ru.gold.ordance.board.model.api.domain.subcategory.SubcategoryUpdateRq;
import ru.gold.ordance.board.model.api.domain.subcategory.WebSubcategory;
import ru.gold.ordance.board.model.entity.domain.Subcategory;

public interface SubcategoryMapper {
    Subcategory fromRequest(SubcategoryUpdateRq rq);

    WebSubcategory fromEntity(Subcategory entity);
}
