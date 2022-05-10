package ru.gold.ordance.board.model.api.domain.client;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import ru.gold.ordance.board.model.api.domain.DeleteByIdRq;

@AllArgsConstructor
@Getter
@ToString
public class ClientDeleteByIdRq implements DeleteByIdRq {
    private static final long serialVersionUID = 1L;

    private final Long entityId;
}
