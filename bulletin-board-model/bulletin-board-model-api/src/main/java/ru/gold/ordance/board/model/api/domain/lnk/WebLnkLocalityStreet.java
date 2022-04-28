package ru.gold.ordance.board.model.api.domain.lnk;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Getter
@ToString
public class WebLnkLocalityStreet {
    private final Long entityId;

    private final Long localityId;

    private final Long streetId;
}
