package ru.gold.ordance.board.model.api.domain.locality;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor(force = true)
@ToString
@Getter
public class WebLocality {
    private final Long entityId;

    private final String name;

    private final Long regionId;
}
