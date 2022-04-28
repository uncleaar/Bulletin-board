package ru.gold.ordance.board.model.api.domain.category;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Getter
@ToString
public class WebCategory {
    private final Long entityId;

    private final String name;
}
