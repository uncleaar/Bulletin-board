package ru.gold.ordance.board.web.api.address;

import lombok.*;
import ru.gold.ordance.board.web.api.UpdateRq;

@Builder
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Getter
@ToString
public class AddressUpdateRq implements UpdateRq {
    private static final long serialVersionUID = 1L;

    private final Long entityId;

    private final Long localityId;

    private final Long streetId;

    private final String houseNumber;
}
