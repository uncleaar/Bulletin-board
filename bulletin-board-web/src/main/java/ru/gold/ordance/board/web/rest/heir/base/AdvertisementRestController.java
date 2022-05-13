package ru.gold.ordance.board.web.rest.heir.base;

import ru.gold.ordance.board.web.rest.AbstractBaseSearchRestController;
import ru.gold.ordance.board.web.rest.AbstractDeleteRestController;
import ru.gold.ordance.board.web.rest.AbstractUpdateRestController;
import ru.gold.ordance.board.web.api.advertisement.AdvertisementDeleteByIdRs;
import ru.gold.ordance.board.web.api.advertisement.AdvertisementGetRs;
import ru.gold.ordance.board.web.api.advertisement.AdvertisementUpdateRq;
import ru.gold.ordance.board.web.api.advertisement.AdvertisementUpdateRs;

public interface AdvertisementRestController extends AbstractBaseSearchRestController<AdvertisementGetRs>, AbstractUpdateRestController<AdvertisementUpdateRq, AdvertisementUpdateRs>, AbstractDeleteRestController<AdvertisementDeleteByIdRs> {
    AdvertisementGetRs findByName(String name);
}
