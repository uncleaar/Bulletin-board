package ru.gold.ordance.board.web.api.advertisement;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import ru.gold.ordance.board.web.api.GetRq;

@AllArgsConstructor
@Getter
@ToString
public class AdvertisementGetByCategoryNameAndRegionNameRq implements GetRq {
    private static final long serialVersionUID = 1L;

    private final String categoryName;

    private final String regionName;
}
