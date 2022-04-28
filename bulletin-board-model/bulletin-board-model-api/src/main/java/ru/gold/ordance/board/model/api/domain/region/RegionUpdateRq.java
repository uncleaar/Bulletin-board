package ru.gold.ordance.board.model.api.domain.region;

import lombok.*;
import ru.gold.ordance.board.model.api.domain.UpdateRq;

@Builder
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Getter
@ToString
public class RegionUpdateRq implements UpdateRq {
    private final Long entityId;

    private final String name;
}
