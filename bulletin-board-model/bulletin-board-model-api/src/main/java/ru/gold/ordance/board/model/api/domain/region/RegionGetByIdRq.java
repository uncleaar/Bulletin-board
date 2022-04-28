package ru.gold.ordance.board.model.api.domain.region;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import ru.gold.ordance.board.model.api.domain.GetByIdRq;

@AllArgsConstructor
@Getter
@ToString
public class RegionGetByIdRq implements GetByIdRq {
    private final Long entityId;

    public RegionGetByIdRq() {
        entityId = null;
    }
}