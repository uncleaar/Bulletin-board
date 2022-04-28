package ru.gold.ordance.board.model.api.domain.street;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import ru.gold.ordance.board.model.api.domain.DeleteByIdRq;

@AllArgsConstructor
@Getter
@ToString
public class StreetDeleteByIdRq implements DeleteByIdRq {
    private final Long entityId;
}
