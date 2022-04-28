package ru.gold.ordance.board.model.api.domain.subcategory;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import ru.gold.ordance.board.model.api.domain.GetByIdRq;

@AllArgsConstructor
@Getter
@ToString
public class SubcategoryGetByIdRq implements GetByIdRq {
    private final Long entityId;
}
