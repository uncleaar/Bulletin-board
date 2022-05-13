package ru.gold.ordance.board.web.api.subcategory;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Getter
@ToString
public class WebSubcategory {
    private static final long serialVersionUID = 1L;

    private final Long entityId;

    private final String name;

    private final Long categoryId;
}
