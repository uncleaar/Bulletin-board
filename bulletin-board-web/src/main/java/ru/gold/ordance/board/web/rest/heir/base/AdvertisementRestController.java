package ru.gold.ordance.board.web.rest.heir.base;

import ru.gold.ordance.board.web.api.advertisement.*;
import ru.gold.ordance.board.web.rest.AbstractBaseSearchRestController;
import ru.gold.ordance.board.web.rest.AbstractDeleteRestController;
import ru.gold.ordance.board.web.rest.AbstractUpdateRestController;

public interface AdvertisementRestController extends AbstractBaseSearchRestController<AdvertisementGetRs>, AbstractUpdateRestController<AdvertisementUpdateRq, AdvertisementUpdateRs>, AbstractDeleteRestController<AdvertisementDeleteByIdRs> {
    AdvertisementGetRs findByCategoryName(String name);
    AdvertisementGetRs findByRegionName(String name);
    AdvertisementGetRs findByCategoryNameAndRegionName(String categoryName, String regionName);
    AdvertisementGetRs findByName(String name);
}
