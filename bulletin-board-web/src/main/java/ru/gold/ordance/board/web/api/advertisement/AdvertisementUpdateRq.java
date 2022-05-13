package ru.gold.ordance.board.web.api.advertisement;

import lombok.*;
import ru.gold.ordance.board.web.api.UpdateRq;

@Builder
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Getter
@ToString
public class AdvertisementUpdateRq implements UpdateRq {
    private static final long serialVersionUID = 1L;

    private final Long entityId;

    private final Long clientId;

    private final String name;

    private final Long subcategoryId;

    private final String description;

    private final int price;

    private final Long localityId;

    private final Long streetId;

    private final String houseNumber;
}
