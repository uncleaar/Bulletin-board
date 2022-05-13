package ru.gold.ordance.board.web.api.address;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import ru.gold.ordance.board.web.api.DeleteByIdRq;

@AllArgsConstructor
@Getter
@ToString
public class AddressDeleteByIdRq implements DeleteByIdRq {
    private static final long serialVersionUID = 1L;

    private final Long entityId;
}
