package ru.gold.ordance.board.model.api.domain.advertisement;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import ru.gold.ordance.board.model.api.domain.GetByNameRq;

@AllArgsConstructor
@Getter
@ToString
public class AdvertisementGetByNameRq implements GetByNameRq {
    private static final long serialVersionUID = 1L;

    private final String name;
}
