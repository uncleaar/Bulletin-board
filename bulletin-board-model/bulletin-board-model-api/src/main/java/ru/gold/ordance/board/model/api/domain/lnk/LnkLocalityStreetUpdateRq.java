package ru.gold.ordance.board.model.api.domain.lnk;

import lombok.*;
import ru.gold.ordance.board.model.api.domain.UpdateRq;

@Builder
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Getter
@ToString
public class LnkLocalityStreetUpdateRq implements UpdateRq {
    private static final long serialVersionUID = 1L;

    private final Long entityId;

    private final Long localityId;

    private final Long streetId;
}
