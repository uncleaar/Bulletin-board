package ru.gold.ordance.board.model.api.domain.address;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Getter
@ToString
public class WebAddress {
    private static final long serialVersionUID = 1L;

    private final Long entityId;

    private final Long localityId;

    private final Long streetId;

    private final String houseNumber;
}
