package ru.gold.ordance.board.web.api.complex;

import lombok.*;
import ru.gold.ordance.board.web.api.UpdateRq;

@Builder
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Getter
@ToString
public class ComplexAddressUpdateRq implements UpdateRq {
    private static final long serialVersionUID = 1L;

    private final String regionName;

    private final String localityName;

    private final String streetName;

    private final String houseNumber;
}
