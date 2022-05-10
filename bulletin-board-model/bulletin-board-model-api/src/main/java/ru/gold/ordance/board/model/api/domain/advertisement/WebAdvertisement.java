package ru.gold.ordance.board.model.api.domain.advertisement;

import lombok.*;

import java.time.LocalDate;

@Builder
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Getter
@ToString
public class WebAdvertisement {
    private static final long serialVersionUID = 1L;

    private final Long entityId;

    private final Long clientId;

    private final String name;

    private final LocalDate createDate;

    private final Long subcategoryId;

    private final String description;

    private final int price;

    private final Long localityId;

    private final Long streetId;

    private final String houseNumber;
}
