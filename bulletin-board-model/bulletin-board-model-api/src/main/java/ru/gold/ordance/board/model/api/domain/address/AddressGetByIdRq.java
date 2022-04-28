package ru.gold.ordance.board.model.api.domain.address;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import ru.gold.ordance.board.model.api.domain.GetByIdRq;

@AllArgsConstructor
@Getter
@ToString
public class AddressGetByIdRq implements GetByIdRq {
    private final Long entityId;
}
