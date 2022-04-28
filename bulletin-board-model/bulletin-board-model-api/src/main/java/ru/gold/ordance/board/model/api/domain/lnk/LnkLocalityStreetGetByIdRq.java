package ru.gold.ordance.board.model.api.domain.lnk;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import ru.gold.ordance.board.model.api.domain.GetByIdRq;

@AllArgsConstructor
@Getter
@ToString
public class LnkLocalityStreetGetByIdRq implements GetByIdRq {
    private final Long entityId;
}
