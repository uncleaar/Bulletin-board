package ru.gold.ordance.board.model.api.domain.lnk;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import ru.gold.ordance.board.model.api.domain.GetRq;

@Builder
@Getter
@ToString
public class LnkLocalityStreetGetByLSRq implements GetRq {
    private static final long serialVersionUID = 1L;

    private final Long localityId;

    private final Long streetId;
}
