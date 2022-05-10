package ru.gold.ordance.board.model.api.domain.subcategory;

import lombok.*;
import ru.gold.ordance.board.model.api.domain.UpdateRq;

@Builder
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Getter
@ToString
public class SubcategoryUpdateRq implements UpdateRq {
    private static final long serialVersionUID = 1L;

    private final Long entityId;

    private final String name;

    private final Long categoryId;
}
