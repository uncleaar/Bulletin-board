package ru.gold.ordance.board.model.api.domain.region;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import ru.gold.ordance.board.model.api.domain.DeleteByIdRq;

@AllArgsConstructor
@Getter
@ToString
public class RegionDeleteByIdRq implements DeleteByIdRq {
    private final Long entityId;
}
